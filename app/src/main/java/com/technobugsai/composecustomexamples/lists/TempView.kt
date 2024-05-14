package com.technobugsai.composecustomexamples.lists

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.technobugsai.composecustomexamples.utils.ComposeExamplesUtils.AnimationState
import com.technobugsai.composecustomexamples.utils.ComposeExamplesUtils.isExpanded

@Composable
fun TempView() {
    var maxDimension by remember {
        mutableStateOf(0f)
    }
    var  animationState by remember {
        mutableStateOf(AnimationState.Collapsed)
    }
    val dimension by animateFloatAsState(
        targetValue = if (animationState.isExpanded()) maxDimension else 0f,
        animationSpec = tween(if (animationState.isExpanded()) 2000 else 1000)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                maxDimension = this.size.maxDimension
                drawCircle(
                    color = Color.Green,
                    radius = dimension,
                    center = this.center
                )
            },
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = !animationState.isExpanded() && (dimension == 0f),
            enter = scaleIn(tween(300)),
            exit = scaleOut()
        ) {
            FloatingActionButton(
                onClick = { animationState = AnimationState.Expanded },
                shape = CircleShape,
                containerColor = Color.Green,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }

        AnimatedVisibility(
            visible = animationState.isExpanded() && (dimension > maxDimension / 1.8),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            FloatingActionButton(
                onClick = { animationState = AnimationState.Collapsed  },
                shape = CircleShape,
                containerColor = Color.White,
                contentColor = Color.Green
            ) {
                Icon(imageVector = Icons.Default.Clear  , contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TempViewPreview() {
    TempView()
}