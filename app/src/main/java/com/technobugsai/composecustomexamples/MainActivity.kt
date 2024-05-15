package com.technobugsai.composecustomexamples

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import coil.size.Dimension
import com.technobugsai.composecustomexamples.bottomnav.BottomArc
import com.technobugsai.composecustomexamples.lists.CartoonList
import com.technobugsai.composecustomexamples.lists.CartoonListNew
import com.technobugsai.composecustomexamples.lists.TempView
import com.technobugsai.composecustomexamples.ui.theme.ComposeCustomExamplesTheme
import com.technobugsai.composecustomexamples.utils.ComposeExamplesUtils
import com.technobugsai.composecustomexamples.utils.ComposeExamplesUtils.isExpanded
import com.technobugsai.composecustomexamples.utils.TestData
import com.technobugsai.composecustomexamples.utils.models.CartoonDataModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCustomExamplesTheme {
                // A surface container using the 'background' color from the theme
//                Greeting("Android")
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var maxDimension by remember {
        mutableStateOf(0f)
    }
    var  animationState by remember {
        mutableStateOf(true)
    }
    var cartoonData by remember {
        mutableStateOf<CartoonDataModel?>(null)
    }
    var currentPage by remember {
        mutableStateOf<Int>(0)
    }
    var prevPage by remember {
        mutableStateOf<Int>(-1)
    }
    var currentColor by remember {
        mutableStateOf<String>(TestData.getCartoonList()[0].color)
    }
    var animationCompleted by remember { mutableStateOf(false) }
    var color: String? = null
//    val dimension by animateFloatAsState(
//        targetValue = if (currentPage == 1) {
//            maxDimension
//        } else {
//            currentColor = TestData.getCartoonList()[currentPage].color
//            0f
//        },
//        animationSpec = tween(if (prevPage != currentPage) 2000 else 1000)
//    )
    val cartoonList = TestData.getCartoonList()
    val listSize = cartoonList.size
    val pagerState = rememberPagerState(pageCount = {
        listSize
    })
    ScalingCircleAnimation(pagerState)
//    AnimatedVisibility(visible = animationState, modifier = Modifier.fillMaxSize()) {
//        Canvas(modifier = Modifier) {
//            maxDimension = this.size.maxDimension
//            drawCircle(
//                color = Color(currentColor.toColorInt() ?: 0),
//                radius = dimension,
//                center = this.center
//            )
//        }
//    }
    CartoonList(onClick = {
        cartoonData = it
    }, onPageSwiped = {
        prevPage = currentPage
        currentPage = it
    }, pagerState)
    BottomArc(Color.Blue)
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScalingCircleAnimation(pagerState: PagerState) {
    val transition = updateTransition(targetState = pagerState.currentPage, label = "Page Transition")

    val scale by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 3000,
                easing = FastOutSlowInEasing
            )
        },
        label = "Circle Scale"
    ) { it ->
        Log.e("Called", it.toString())
//        if (it == pagerState.targetPage) 3f else 0f // Target scale factor
        // Scale values based on the target page
        if (pagerState.currentPageOffsetFraction == 0f) 4f else 0.5f
    }

    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        val radius = size.minDimension / 3 * scale
        drawCircle(
            color = Color(TestData.getCartoonList()[pagerState.currentPage].color.toColorInt()),
            radius = radius,
            center = Offset(size.width / 2, size.height / 2)
        )
    })
}

fun returnDimension(color: String, currentColor: String, maxDimension: Float): Float {
    return if (currentColor == color) {
        maxDimension
    } else {
        0f
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeCustomExamplesTheme {
        Greeting("Android")
    }
}