package com.technobugsai.composecustomexamples.animatedpager

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.technobugsai.composecustomexamples.bottomnav.BottomArc
import com.technobugsai.composecustomexamples.lists.CartoonList
import com.technobugsai.composecustomexamples.utils.TestData
import com.technobugsai.composecustomexamples.viewmodel.NewViewModel
import kotlinx.coroutines.launch

@Composable
fun PagerItem(newViewModel: NewViewModel = viewModel()) {
    MainAnimation(newViewModel)
    Column {
        CartoonList(viewModel = newViewModel)
        BottomArc()
    }
}

@Composable
fun MainAnimation(newViewModel: NewViewModel) {
    // Animate shape which will help us scale the circle in the bg from 0 to its max -> 1300f
    // Initial value is 0 which will make it invisible in the start
    val animateShape = remember {
        Animatable(0f)
    }

    if (animateShape.value >= TestData.MAX_SCALE_SIZE) {
        LaunchedEffect(animateShape) {
            animateShape.stop() // Stop any ongoing animation
            animateShape.snapTo(0f) // Reset the value to initial state
        }
    }

    // Is animation still in progress?
    // ViewModel has a delay which is same as the animation progress to keep delay for the animation state
    val animationProgress by newViewModel.isAnimationInProgress.collectAsState()
    // Co routine scope is used to work with LaunchedEffect
    val scope = rememberCoroutineScope()
    LaunchedEffect(animationProgress) {
        if (animationProgress) {
            scope.launch {
                animateShape.animateTo(
                    targetValue = TestData.MAX_SCALE_SIZE,
                    animationSpec = repeatable(
                        animation = tween(
                            durationMillis = TestData.ANIMATION_DURATION.toInt(),
                            easing = LinearEasing,
                        ),
                        iterations = 1
                    )
                )
            }
        }
    }

    // Draw and scale circle animation in the bg
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