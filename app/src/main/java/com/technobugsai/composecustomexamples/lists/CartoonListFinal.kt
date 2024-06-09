package com.technobugsai.composecustomexamples.lists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.technobugsai.composecustomexamples.utils.TestData
import com.technobugsai.composecustomexamples.utils.models.CartoonDataModel
import com.technobugsai.composecustomexamples.viewmodel.NewViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartoonListFinal(viewModel: NewViewModel?) {
    val cartoonList = TestData.getCartoonList()
    val listSize = cartoonList.size
    val pagerState = rememberPagerState(pageCount = {
        listSize
    })
    HorizontalPager(modifier = Modifier.fillMaxHeight(0.8f), state = pagerState) {pageIndex ->
        CartoonItemFinal(cartoonDataModel = cartoonList[pageIndex])
        pagerState.run {
            viewModel?.updateStateForAnimation(
                currentPage,
                settledPage,
                targetPage,
                pagerState.currentPageOffsetFraction
            )
        }
        if (!pagerState.isScrollInProgress) {
            viewModel?.updateState(pagerState.settledPage)
        }
    }
}

@Composable
fun CartoonItemFinal(cartoonDataModel: CartoonDataModel) {
    Column {
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            color = Color.White,
            text = cartoonDataModel.topTitle.uppercase(),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(20.dp))
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = cartoonDataModel.imgPath),
            contentDescription = cartoonDataModel.name
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            color = Color.White,
            text = cartoonDataModel.name,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun CartoonItemFinalPreview(cartoonDataModel: CartoonDataModel = TestData.getCartoonList()[0]) {
    CartoonItemFinal(cartoonDataModel)
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun CartoonListFinalPreview() {
    CartoonListFinal(null)
}