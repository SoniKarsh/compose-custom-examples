package com.technobugsai.composecustomexamples.shapes

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class CircleShape(val centerX: Float,
                  val centerY: Float,
                  val radius: Float): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = getCirclePath(centerX, centerY, radius))
    }

    private fun getCirclePath(
        centerX: Float,
        centerY: Float,
        radius: Float,
        padding: Float = 0f
    ): Path {
        // Calculate Vertices
        return Path().apply {
            moveTo(centerX + radius, centerY) // Move to starting point
            arcTo(
                rect = Rect(
                    left = centerX - radius,
                    top = centerY - radius,
                    right = centerX + radius,
                    bottom = centerY + radius
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 360f,
                forceMoveTo = false
            )
        }
    }
}