package com.skyyo.igdbbrowser.features.signIn.bottomSheets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetScreen() {
    val list = arrayListOf<Int>()
    repeat(200) { list += it }

    LazyColumn {
        itemsIndexed(list) { index, launch ->
            Text(text = "Bottom Sheet Screen")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

