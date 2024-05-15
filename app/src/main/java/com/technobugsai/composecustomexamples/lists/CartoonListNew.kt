package com.technobugsai.composecustomexamples.lists

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technobugsai.composecustomexamples.R
import com.technobugsai.composecustomexamples.utils.ComposeExamplesUtils
import com.technobugsai.composecustomexamples.utils.ComposeExamplesUtils.isExpanded
import com.technobugsai.composecustomexamples.utils.TestData
import com.technobugsai.composecustomexamples.utils.models.CartoonDataModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartoonListNew(onClick: (CartoonDataModel) -> Unit) {
    val cartoonList = TestData.getCartoonList()
    val listSize = cartoonList.size
    val pagerState = rememberPagerState(pageCount = {
        listSize
    })
    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow {  }.collect { page ->
            // Do something with each page change, for example:
            // viewModel.sendPageSelectedEvent(page)
            Log.d("Page change", "Page changed to $page")
        }
    }
    HorizontalPager(
        modifier = Modifier.wrapContentHeight(align = Alignment.Top),
        state = pagerState
    ) {
        CartoonItemNew(
            cartoonDataModel = cartoonList[it],
            onClick
        )
    }
}

@Composable
fun CartoonItemNew(cartoonDataModel: CartoonDataModel, onClick: (CartoonDataModel) -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val density = LocalDensity.current

    Column {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
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

@Preview(showBackground = true)
@Composable
fun TempView1Preview() {
    TempView1()
}

@Composable
fun ShapeWithCircularCutout(
    modifier: Modifier = Modifier,
    circleRadius: Float,
    circleCenter: Offset
) {
    Canvas(modifier = modifier) {
        // Define the size of the canvas
        val canvasWidth = size.width
        val canvasHeight = size.height
        // Define a path for the outer rectangle
        val outerRectPath = Path().apply {
            addRect(Rect(0f, 0f, canvasWidth, canvasHeight))
        }

        // Define a path for the circular cutout
        val circularCutoutPath = Path().apply {
            addOval(
                Rect(
                    circleCenter.x - circleRadius,
                    circleCenter.y - circleRadius,
                    circleCenter.x + circleRadius,
                    circleCenter.y + circleRadius
                )
            )
        }

        // Clip the path to create the circular cutout
        clipPath(circularCutoutPath, clipOp = ClipOp.Difference) {
            // Draw your background shape here (e.g., a rectangle)
            drawRect(
                color = Color.Blue,
                topLeft = Offset.Zero,
                size = Size(size.width, size.height)
            )
        }
    }
}



@Preview
@Composable
fun TempView1() {
    ShapeWithCircularCutout(
        modifier = Modifier.fillMaxSize(),
        circleRadius = 100f,
        circleCenter = Offset(200f, 200f)
    )
}

@Preview
@Composable
fun CartoonItemNewPreview(cartoonDataModel: CartoonDataModel = TestData.getCartoonList()[0]) {
    CartoonItemNew(cartoonDataModel, {})
}

@Preview(showBackground = true)
@Composable
fun CartoonListNewPreview() {
    CartoonListNew({})
}