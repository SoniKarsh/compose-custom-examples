package com.technobugsai.composecustomexamples

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.technobugsai.composecustomexamples.bottomnav.BottomArcNew
import com.technobugsai.composecustomexamples.lists.CartoonListFinal
import com.technobugsai.composecustomexamples.ui.theme.ComposeCustomExamplesTheme
import com.technobugsai.composecustomexamples.utils.TestData
import com.technobugsai.composecustomexamples.viewmodel.NewViewModel
import kotlinx.coroutines.launch

class NewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCustomExamplesTheme {
                MainViewBackground()
            }
        }
    }
}

@Composable
fun MainViewBackground(newViewModel: NewViewModel = viewModel()) {
    val pageUiState by newViewModel.uiState.collectAsState()
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
//        color = Color.Black
        color = Color(pageUiState.bgColor.toColorInt())
    ) {
        MainView(newViewModel)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScaleAnimationView(newViewModel: NewViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    val animatable = remember { Animatable(0f) }

    // Function to reset and restart the animation
    fun restartAnimation() {
        scope.launch {
            animatable.stop() // Stop any ongoing animation
            animatable.snapTo(0f) // Reset the value to initial state
            animatable.animateTo(
                targetValue = 15f,
                animationSpec = tween(durationMillis = 1000)
            ) // Restart the animation
        }
    }  

    val animationProgress by newViewModel.isAnimationInProgress.collectAsState()
    val scale by animateFloatAsState(
        targetValue = if (animationProgress) 15f else 0f,
        animationSpec = tween(durationMillis = TestData.ANIMATION_DURATION.toInt())
    )
    if (!animationProgress) {
        restartAnimation()
    }
    AnimatedVisibility(visible = animationProgress) {
        Log.e("Called121", (transition.currentState == transition.targetState).toString())
        Log.e("Called12is", transition.isRunning.toString())
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Log.e("Called", animationProgress.toString())
            Log.e("CalledScale", scale.toString())
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val radius = 100f * scale
                drawCircle(
                    color = Color(newViewModel.getCurrentPage().color.toColorInt()),
                    radius = radius
                )
            }
        }
    }
}

@Composable
fun MainView(newViewModel: NewViewModel?) {
    ScaleAnimationView(newViewModel ?: viewModel())
    Column {
        CartoonListFinal(viewModel = newViewModel)
        BottomArcNew()
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    MainView(null)
}