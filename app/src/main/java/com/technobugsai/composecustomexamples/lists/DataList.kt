package com.technobugsai.composecustomexamples.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technobugsai.composecustomexamples.shapes.Heptagon
import com.technobugsai.composecustomexamples.shapes.HeptagonWithImage
import com.technobugsai.composecustomexamples.utils.TestData
import com.technobugsai.composecustomexamples.utils.models.DataModel


@Composable
fun DataList() {
    LazyRow {
        items(TestData.getList()) { data ->
            DataItem(data = data)
        }
    }
}

@Composable
fun DataItem(data: DataModel) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeptagonWithImage(100f, 100f, 100f, Color.Green, data.localPath)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            Text(text = data.name, textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DataListPreview() {
    DataList()
}