package com.technobugsai.composecustomexamples.bottomnav

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomArc(bgColor: Color) {
    Box(
        modifier = Modifier.fillMaxSize().background(bgColor),
        contentAlignment = Alignment.BottomCenter,
    ) {
        // Give canvas full width and height
        Canvas(modifier = Modifier.matchParentSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            // Double the width to look like it goes beyond mobile width and looks
            // as we want, otherwise it will look like a semi circle which we don't want
            val doubleWidth = (canvasWidth * 2)
            // To reach the center point on the x-axis
            val xPos = canvasWidth / 2
            Log.e("Called", "canvasWidth $canvasWidth canvasHeight $canvasHeight formWidth $doubleWidth xPos $xPos")
            drawArc(
                Color.Black,
                0f,
                360f,
                useCenter = true,
                size = Size(canvasWidth * 1.5f, canvasWidth * 1.5f),
                // Here giving offset from topLeft to -xPos will move
                // The arc to its left which is opposite of positive xPos
                // canvasHeight would make it go below screen visibility
                // To avoid we will use -300
                // As it is a half arc we are using 600 / 2 value to subtract from canvas height
                // Otherwise it will also show half space as blank
                // Note: Had to change the logic but the above explanation would be enough for you to understand
                topLeft = Offset(x = -xPos * 0.5f, y = canvasHeight - 400f)
            )
        }
        AddFabIcon(bgColor)
    }
}

@Preview
@Composable
fun AddFabIcon(bgColor: Color = Color.Blue) {
    Box(modifier = Modifier
        .offset(y = (-70).dp)
        .background(bgColor, CircleShape)) {
        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .size(130.dp)
                .background(bgColor, CircleShape)
                .padding(20.dp),
            shape = CircleShape,
            containerColor = Color.White,
            contentColor = Color.Black,

            ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }
}

@Composable
fun BottomCenterPoint() {
    BoxWithConstraints {
        // Convert constraints to DPs using LocalDensity
        val screenWidth = with(LocalDensity.current) { maxWidth.toPx() }
        val screenHeight = with(LocalDensity.current) { maxHeight.toPx() }

        // Calculate the center bottom point
        val centerX = screenWidth / 2
        val centerY = screenHeight

        // You can use these coordinates as needed
        // For example, use them to position a composable or for some custom drawing
        // Here, we're just printing them out
        println("Center bottom X: $centerX")
        println("Center bottom Y: $centerY")
    }
}

@Preview (showBackground = true)
@Composable
fun BottomArcPreview() {
    BottomArc(Color.Blue)
}