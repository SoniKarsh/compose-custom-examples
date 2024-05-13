package com.technobugsai.composecustomexamples.lists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technobugsai.composecustomexamples.utils.TestData
import com.technobugsai.composecustomexamples.utils.models.CartoonDataModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartoonList() {
    val cartoonList = TestData.getCartoonList()
    val listSize = cartoonList.size
    val pagerState = rememberPagerState(pageCount = {
        listSize
    })
    HorizontalPager(
        modifier = Modifier.wrapContentHeight(align = Alignment.Top),
        state = pagerState
    ) {
        CartoonItem(cartoonDataModel = cartoonList[it])
    }
}

@Composable
fun CartoonItem(cartoonDataModel: CartoonDataModel) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val density = LocalDensity.current
    val brush = Brush.verticalGradient(
        colors = listOf(
            Color(android.graphics.Color.parseColor("#b20811")),
            Color(android.graphics.Color.parseColor("#FFFFFF"))
        ),
        startY = 0f,
        endY = with(density) { screenHeight.toPx() * 10f })
    Column(Modifier.background(brush)) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = cartoonDataModel.imgPath),
            contentDescription = cartoonDataModel.name
        )
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            color = Color.White,
            text = cartoonDataModel.name,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun CartoonItemPreview(cartoonDataModel: CartoonDataModel = TestData.getCartoonList()[0]) {
    CartoonItem(cartoonDataModel)
}

@Preview(showBackground = true)
@Composable
fun CartoonListPreview() {
    CartoonList()
}