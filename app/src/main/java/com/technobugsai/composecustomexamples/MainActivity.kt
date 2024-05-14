package com.technobugsai.composecustomexamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
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
import com.technobugsai.composecustomexamples.utils.models.CartoonDataModel

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
    var color: String? = null
    val dimension by animateFloatAsState(
        targetValue = if (cartoonData?.color == color) {
            maxDimension
        } else {
            color = cartoonData?.color
            0f
        },
        animationSpec = tween(if (animationState) 2000 else 1000)
    )
    AnimatedVisibility(visible = animationState, modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier) {
            maxDimension = this.size.maxDimension
            drawCircle(
                color = Color(color?.toColorInt() ?: 0),
                radius = dimension,
                center = this.center
            )
        }
    }
    CartoonList { cartoonDataModel ->
        cartoonData = cartoonDataModel
    }
    BottomArc(Color.Blue)
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
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