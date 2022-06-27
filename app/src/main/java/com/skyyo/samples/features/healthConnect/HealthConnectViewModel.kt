package com.skyyo.samples.features.healthConnect

import android.app.Application
import android.os.RemoteException
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthDataRequestPermissions
import androidx.health.connect.client.permission.Permission
import androidx.health.connect.client.records.*
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

private const val LAST_WRITTEN_RECORD_UID_KEY = "lastWrittenRecordUid"
private const val STEPS_WRITTEN_KEY = "stepsWritten"
private const val STEPS_READ_KEY = "stepsRead"
private const val THIRD_PARTY_STEPS_KEY = "3rdPartySteps"
private const val ARE_ALL_PERMISSIONS_GRANTED_KEY = "areAllPermissionsGranted"

@HiltViewModel
class HealthConnectViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    val application: Application,
): ViewModel() {
    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(application.applicationContext) }
    val permissions = setOf(
        Permission.createReadPermission(Steps::class),
        Permission.createWritePermission(Steps::class),
        Permission.createReadPermission(ActivitySession::class)
    )
    private val lastWrittenRecordUid = handle.getStateFlow<String?>(LAST_WRITTEN_RECORD_UID_KEY, null)
    val stepsWritten = handle.getStateFlow(STEPS_WRITTEN_KEY, 1L)
    val stepsRead = handle.getStateFlow<Long?>(STEPS_READ_KEY, null)
    val localStepsCanBeRead = lastWrittenRecordUid.map { it != null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)
    val accumulated3rdPartySteps = handle.getStateFlow(THIRD_PARTY_STEPS_KEY, 0L)
    val areAllPermissionsGranted = handle.getStateFlow( ARE_ALL_PERMISSIONS_GRANTED_KEY, false)

    suspend fun checkPermissions() {
        handle[ARE_ALL_PERMISSIONS_GRANTED_KEY] = healthConnectClient.hasAllPermissions(permissions)
    }

    /**
     * Determines whether all the specified permissions are already granted. It is recommended to
     * call [PermissionController.getGrantedPermissions] first in the permissions flow, as if the
     * permissions are already granted then there is no need to request permissions via
     * [HealthDataRequestPermissions].
     */
    private suspend fun HealthConnectClient.hasAllPermissions(permissions: Set<Permission>): Boolean {
        return permissions == permissionController.getGrantedPermissions(
            permissions
        )
    }

    fun writeSteps() = viewModelScope.launch(Dispatchers.IO) {
        handle[LAST_WRITTEN_RECORD_UID_KEY] = healthConnectClient.writeSteps(stepsWritten.value)
        handle[STEPS_WRITTEN_KEY] = stepsWritten.value + 1
    }

    fun readSteps() = viewModelScope.launch(Dispatchers.IO) {
        val recordUid = lastWrittenRecordUid.value
        if (recordUid != null) {
            handle[STEPS_READ_KEY] = healthConnectClient.readSteps(recordUid)
        }
    }

    fun read3rdPartySteps(activitySessionUid: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val activitySession =
                healthConnectClient.readRecord(ActivitySession::class, activitySessionUid)
            // Use the start time and end time from the session, for reading raw and aggregate data.
            val timeRangeFilter = TimeRangeFilter.between(
                startTime = activitySession.record.startTime,
                endTime = activitySession.record.endTime
            )
            val aggregateDataTypes = setOf(Steps.COUNT_TOTAL)
            // Limit the data read to just the application that wrote the session. This may or may not
            // be desirable depending on the use case: In some cases, it may be useful to combine with
            // data written by other apps.
            val dataOriginFilter = listOf(activitySession.record.metadata.dataOrigin)
            val aggregateRequest = AggregateRequest(
                metrics = aggregateDataTypes,
                timeRangeFilter = timeRangeFilter,
                dataOriginFilter = dataOriginFilter
            )
            val aggregateData = healthConnectClient.aggregate(aggregateRequest)
            handle[THIRD_PARTY_STEPS_KEY] = aggregateData[Steps.COUNT_TOTAL] ?: 0L
        } catch (e: IllegalArgumentException) {
            //activity session uid is empty or null
            handle[THIRD_PARTY_STEPS_KEY] = 0L
        } catch (e: RemoteException) {
            //activity session uid not exists
            handle[THIRD_PARTY_STEPS_KEY] = 0L
        }
    }

    private suspend fun HealthConnectClient.writeSteps(count: Long): String {
        val records = mutableListOf<Record>()
        val now = ZonedDateTime.now()
        records.add(
            Steps(
                count = count,
                startTime = now.minusHours(2).toInstant(),
                startZoneOffset = now.offset,
                endTime = now.toInstant(),
                endZoneOffset = now.offset
            )
        )
        return insertRecords(records).recordUidsList[0]
    }

    private suspend fun HealthConnectClient.readSteps(recordUid: String): Long {
        return readRecord(Steps::class, recordUid).record.count
    }
}