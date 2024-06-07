package com.technobugsai.composecustomexamples

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.technobugsai.composecustomexamples.bottomnav.BottomArcNew
import com.technobugsai.composecustomexamples.lists.CartoonListFinal
import com.technobugsai.composecustomexamples.ui.theme.ComposeCustomExamplesTheme
import com.technobugsai.composecustomexamples.viewmodel.NewViewModel
import kotlinx.coroutines.launch

class AnimationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCustomExamplesTheme {
                MainViewAnimationBackground()
            }
        }
    }
}

@Composable
fun MainViewAnimationBackground(newViewModel: NewViewModel = viewModel()) {
    val pageUiState by newViewModel.uiState.collectAsState()
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(newViewModel.getCurrentPage().color.toColorInt())
    ) {
        MainAnimationView(newViewModel, pageUiState.bgColor.toColorInt())
    }
}

@Preview
@Composable
fun MainViewAnimationBackgroundPreview() {
    MainViewAnimationBackground()
}

@Composable
fun MainAnimation(newViewModel: NewViewModel = viewModel(), color: Int) {
    val animateShape = remember {
        Animatable(0f)
    }
    val maxShape = 1300f
    LaunchedEffect(animateShape) {
        Log.e("CalledX", animateShape.value.toString())
        animateShape.animateTo(
            targetValue = maxShape,
            animationSpec = tween(
                durationMillis = 2000,
                easing = LinearEasing,
            )
        )
    }
    val scope = rememberCoroutineScope()
    Log.e("calledss", animateShape.value.toString())
    if (animateShape.value >= maxShape - 5) {
        LaunchedEffect(animateShape) {
            scope.launch {
                animateShape.stop() // Stop any ongoing animation
                animateShape.snapTo(0f) // Reset the value to initial state
                Log.e("calledss1", "Caasasasas")
            }
        }
    }
    val animationProgress by newViewModel.isAnimationInProgress.collectAsState()
    Log.e("calledasasasasaas", animationProgress.toString())
    LaunchedEffect(animationProgress) {

        if (animationProgress) {
            Log.e("called", animationProgress.toString())
            scope.launch {
                if (animateShape.value > 0) {
                    animateShape.stop() // Stop any ongoing animation
                    animateShape.snapTo(0f) // Reset the value to initial state
                } else {
                    animateShape.animateTo(
                        targetValue = maxShape,
                        animationSpec = repeatable(
                            animation = tween(
                                durationMillis = 2000,
                                easing = LinearEasing,
                            ),
                            iterations = 1
                        )
                    ) // Restart the animation
                }
            }
        }
//        else if(!animationProgress) {
//            Log.e("called1", animationProgress.toString())
//            scope.launch {
//                animateShape.stop() // Stop any ongoing animation
//                animateShape.snapTo(0f) // Reset the value to initial state
//            }
//        }
    }
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val radius = animateShape.value
        drawCircle(
            color = Color(newViewModel.getCurrentPage().color.toColorInt()),
            radius = radius
        )
    }

}
@Composable
fun MainAnimationView(newViewModel: NewViewModel = viewModel(), color: Int) {
    MainAnimation(newViewModel, color)
    Column {
        CartoonListFinal(viewModel = newViewModel)
        BottomArcNew()
    }
}

@Preview
@Composable
fun MainAnimationViewPreview() {
//    MainAnimationView(null, -1)
}

//@Preview
@Composable
fun MainAnimationPreview() {
//    MainAnimation(null, -1)
}

@Composable
fun MainBox() {
    val animateShape = remember { Animatable(28f) }
    val initialColor = Color(0xFF302522)
    val targetColor = Color(0xFFede0dc)

    val animateColor = remember { Animatable(initialColor) }

    LaunchedEffect(animateColor) {
        animateColor.animateTo(
            targetValue = targetColor,
            animationSpec = repeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = LinearEasing,
                    delayMillis = 500
                ),
                repeatMode = RepeatMode.Restart,
                iterations = 3
            )
        )
    }

    LaunchedEffect(animateShape) {
        animateShape.animateTo(
            targetValue = 2f,
            animationSpec = repeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = LinearEasing,
                    delayMillis = 500
                ),
                iterations = 1
            )
        )
    }

    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(color = Color(0xFF302522))
            .border(
                width = Dp(animateShape.value),
                color = animateColor.asState().value,
                shape = CircleShape
            )
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_compose),
//            contentDescription = "Compose image",
//            modifier = Modifier
//                .size(56.dp)
//                .align(alignment = Alignment.Center)
//        )
    }
}

//@Preview
@Composable
fun MainBoxPreview() {
    MainBox()
}