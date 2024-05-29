package com.technobugsai.composecustomexamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.technobugsai.composecustomexamples.bottomnav.BottomArcNew
import com.technobugsai.composecustomexamples.lists.CartoonListFinal
import com.technobugsai.composecustomexamples.ui.theme.ComposeCustomExamplesTheme
import com.technobugsai.composecustomexamples.viewmodel.NewViewModel

class TempActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCustomExamplesTheme {
                TempViewBackground()
            }
        }
    }
}

@Composable
fun TempViewBackground(newViewModel: NewViewModel = viewModel()) {
    val pageUiState by newViewModel.uiState.collectAsState()
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(pageUiState.bgColor.toColorInt())
    ) {
        TempView(newViewModel)
    }
}

@Composable
fun TempView(newViewModel: NewViewModel?) {
    var startAnimation by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 15f else 1f,
        animationSpec = tween(durationMillis = 2000)
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val radius = 100f * scale
            drawCircle(
                color = Color.Red,
                radius = radius
            )
        }

        Button(
            onClick = { startAnimation = true },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = "Start Animation")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TempViewPreview() {
    TempView(null)
}