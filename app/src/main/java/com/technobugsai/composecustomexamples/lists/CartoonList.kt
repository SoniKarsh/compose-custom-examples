package com.technobugsai.composecustomexamples.lists

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technobugsai.composecustomexamples.utils.ComposeExamplesUtils
import com.technobugsai.composecustomexamples.utils.ComposeExamplesUtils.isExpanded
import com.technobugsai.composecustomexamples.utils.TestData
import com.technobugsai.composecustomexamples.utils.models.CartoonDataModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartoonList(onClick: (CartoonDataModel) -> Unit) {
    val cartoonList = TestData.getCartoonList()
    val listSize = cartoonList.size
    val pagerState = rememberPagerState(pageCount = {
        listSize
    })

    // Animation
    var maxDimension by remember {
        mutableStateOf(0f)
    }
    var  animationState by remember {
        mutableStateOf(ComposeExamplesUtils.AnimationState.Collapsed)
    }
    val dimension by animateFloatAsState(
        targetValue = if (animationState.isExpanded()) maxDimension else 0f,
        animationSpec = tween(if (animationState.isExpanded()) 2000 else 1000)
    )

    HorizontalPager(
        modifier = Modifier.wrapContentHeight(align = Alignment.Top),
        state = pagerState
    ) {
        CartoonItem(cartoonDataModel = cartoonList[it], onClick)
    }
}

@Composable
fun CartoonItem(cartoonDataModel: CartoonDataModel, onClick: (CartoonDataModel) -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val density = LocalDensity.current
    Column {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = cartoonDataModel.imgPath),
            contentDescription = cartoonDataModel.name
        )
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .clickable { onClick.invoke(cartoonDataModel) },
            color = Color.Black,
            text = cartoonDataModel.name,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun CartoonItemPreview(cartoonDataModel: CartoonDataModel = TestData.getCartoonList()[0]) {
    CartoonItem(cartoonDataModel, {})
}

@Preview(showBackground = true)
@Composable
fun CartoonListPreview() {
    CartoonList({})
}