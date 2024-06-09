package com.technobugsai.composecustomexamples

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.technobugsai.composecustomexamples.bottomnav.BottomArcNew
import com.technobugsai.composecustomexamples.lists.CartoonListFinal
import com.technobugsai.composecustomexamples.ui.theme.ComposeCustomExamplesTheme
import com.technobugsai.composecustomexamples.utils.TestData
import com.technobugsai.composecustomexamples.utils.TestData.ANIMATION_DURATION
import com.technobugsai.composecustomexamples.utils.TestData.MAX_SCALE_SIZE
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

    // Is animation still in progress?
    // ViewModel has a delay which is same as the animation progress to keep delay for the animation state
    val bgColorPos by newViewModel.currentPos.collectAsState()
    Log.e("Color", TestData.getCartoonList()[bgColorPos].color)
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(TestData.getCartoonList()[bgColorPos].color.toColorInt())
    ) {
        MainAnimationView(newViewModel, pageUiState.bgColor.toColorInt())
    }
}

@Preview
@Composable
fun MainViewAnimationBackgroundPreview() {
    MainViewAnimationBackground()
}

suspend fun animateTo(animateShape: Animatable<Float, AnimationVector1D>) {
    animateShape.animateTo(
        targetValue = MAX_SCALE_SIZE,
        animationSpec = tween(
            durationMillis = ANIMATION_DURATION.toInt(),
            easing = LinearEasing,
        )
    )
}

@Composable
fun MainAnimation(newViewModel: NewViewModel = viewModel(), color: Int) {
    // Animate shape which will help us scale the circle in the bg from 0 to its max -> 1300f
    // Initial value is 0 which will make it invisible in the start
    val animateShape = remember {
        Animatable(0f)
    }

    // Co routine scope is used to work with LaunchedEffect
    val scope = rememberCoroutineScope()
    if (animateShape.value >= MAX_SCALE_SIZE) {
        LaunchedEffect(animateShape) {
//            scope.launch {
                animateShape.stop() // Stop any ongoing animation
                animateShape.snapTo(0f) // Reset the value to initial state
                Log.e("calledss1", "Caasasasas")
//            }
        }
    }
    // Is animation still in progress?
    // ViewModel has a delay which is same as the animation progress to keep delay for the animation state
    val animationProgress by newViewModel.isAnimationInProgress.collectAsState()

    LaunchedEffect(animationProgress) {
        if (animationProgress) {
            Log.e("called", animationProgress.toString())
            scope.launch {
//                if (animateShape.value > 0) {
//                    animateShape.stop() // Stop any ongoing animation
//                    animateShape.snapTo(0f) // Reset the value to initial state
//                } else {
                animateShape.animateTo(
                    targetValue = MAX_SCALE_SIZE,
                    animationSpec = repeatable(
                        animation = tween(
                            durationMillis = ANIMATION_DURATION.toInt(),
                            easing = LinearEasing,
                        ),
                        iterations = 1
                    )
                ) // Restart the animation
//                }
            }
        }
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