package com.technobugsai.composecustomexamples.animatedpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.technobugsai.composecustomexamples.ui.theme.AnimatedPagerTheme
import com.technobugsai.composecustomexamples.utils.TestData
import com.technobugsai.composecustomexamples.viewmodel.NewViewModel

class PagerAnimationActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // To enable entire system window including the status bar
        enableEdgeToEdge()
        setContent {
            // Theme to give us transparent status bar
            AnimatedPagerTheme {
                AnimatedPager()
            }
        }
    }
}

@Composable
fun AnimatedPager(newViewModel: NewViewModel = viewModel()) {
    // currentPos will get updated based on the transition of the pages and animation completion
    val bgColorPos by newViewModel.currentPos.collectAsState()
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(TestData.getCartoonList()[bgColorPos].color.toColorInt())
    ) {
        PagerItem(newViewModel)
    }
}

@Preview
@Composable
fun AnimatedPagerPreview() {
    AnimatedPager()
}