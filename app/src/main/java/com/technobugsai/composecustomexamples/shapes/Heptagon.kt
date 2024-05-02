package com.technobugsai.composecustomexamples.shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Heptagon() {
    // Draw path
    Canvas(modifier = Modifier.size(75.dp).rotate(180f)) {
        drawPath(
            path = getHeptagonPath(),
            color = Color.Green,
            style = Stroke(width = 2f)
        )
    }
}

private fun getHeptagonPath(): Path {
    val centerX = 100f
    val centerY = 100f
    val radius = 100f

    // Calculate Vertices
    val vertices = mutableListOf<Offset>()
    repeat(7) { index ->
        val angle = PI / 2 + 2 * PI / 7 * index
        val x = centerX + radius * cos(angle).toFloat()
        val y = centerY + radius * sin(angle).toFloat()
        vertices.add(Offset(x, y))
    }

    // Draw heptagon
    val path = Path()
    path.moveTo(vertices[0].x, vertices[0].y)
    for (i in 1 until vertices.size) {
        path.lineTo(vertices[i].x, vertices[i].y)
    }
    path.close()
    return path
}

@Preview
@Composable
fun PreviewHeptagon() {
    Heptagon()
}