package com.technobugsai.composecustomexamples.shapes

import android.util.TypedValue
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.technobugsai.composecustomexamples.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Heptagon(
    centerX: Float,
    centerY: Float,
    radius: Float,
    color: Color
) {
    // Draw path
    Canvas(modifier = Modifier.size(75.dp)) {
        drawPath(
            path = getHeptagonPath(centerX, centerY, radius),
            color = Color.Green,
            style = Stroke(width = 2f)
        )
    }
}

@Composable
fun HeptagonWithImage(
    centerX: Float,
    centerY: Float,
    radius: Float,
    color: Color,
    imgId: Int
) {
    val context = LocalContext.current
    val path = getHeptagonPath(centerX, centerY, radius)
    val density = LocalDensity.current
    val dpValueW = with(density) { (2 * radius).toDp() }
    val dpValueH = with(density) { (2 * radius).toDp() }

    // Place image here with clipping
    Image(
        painter = painterResource(id = imgId),
        contentDescription = "Data image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(dpValueW, dpValueH)
            .background(Color.White)
            .clip(HeptagonShape(path))
    )
}

private fun getHeptagonPath(
    centerX: Float,
    centerY: Float,
    radius: Float
): Path {
    // Calculate Vertices
    val vertices = mutableListOf<Offset>()
    repeat(7) { index ->
        val angle = PI / 2 + 2 * PI / 7 * index
        val x = centerX + radius * cos(angle).toFloat()
        val y = centerY + radius * sin(angle).toFloat()
        vertices.add(Offset(x, (2 * radius) - y))
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
    HeptagonWithImage(100f, 100f, 100f, Color.Green, R.drawable.ic_launcher_background)
}