package com.technobugsai.composecustomexamples.shapes

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.technobugsai.composecustomexamples.R
import com.technobugsai.composecustomexamples.utils.models.DataModel
import kotlinx.coroutines.Dispatchers
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
    data: DataModel
) {
    val context = LocalContext.current
    val path = getHeptagonPath(centerX, centerY, radius)
    val pathR = getHeptagonPath(centerX, centerY, radius, 15f)
    val diameter = (2 * radius).toInt()
    val density = LocalDensity.current
    val dpValueW = with(density) { (2 * radius).toDp() }
    val dpValueH = with(density) { (2 * radius).toDp() }

    // ImageRequest with coil
    // When set with listener it creates an issue need to check setting listener.
    val painter = rememberAsyncImagePainter(
        model = createImageRequest(data, context, diameter)
    )

    val dataSelected = remember {
        mutableStateOf(data.isChecked)
    }

    Box(
        modifier = Modifier
            .size(dpValueW, dpValueH)
            .background(Color.White)
            .border(0.5.dp, Color.Black, HeptagonShape(path))
            .clip(
                HeptagonShape(path)
            )
            .clickable(
                onClick = {
                    data.isChecked = !data.isChecked
                    dataSelected.value = data.isChecked
                }
            )
    ) {
        if (painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.Center)
                .width(15.dp)
                .height(15.dp),
                strokeWidth = 2.dp
            )
        } else {
            Image(
                modifier = Modifier
                    .size(dpValueW, dpValueH),
                painter = painter,
                contentDescription = data.name,
                contentScale = ContentScale.Crop,
            )
        }
        if (dataSelected.value) {
            Surface(
                modifier = Modifier
                    .size(dpValueW, dpValueH)
                    .alpha(0.5f)
                    .clip(
                        HeptagonShape(pathR)
                    ),
                color = Color.Black
            ) {
                Image(
                    modifier = Modifier
                        .padding(15.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.ic_checked),
                    contentDescription = stringResource(id = R.string.checked_icon)
                )
            }
        }
    }
}

private fun createImageRequest(data: DataModel, context: Context, diameter: Int): ImageRequest {
    return ImageRequest.Builder(context)
        .data(data.url)
        .size(diameter, diameter)
        .dispatcher(Dispatchers.IO)
        .memoryCacheKey(data.url)
        .diskCacheKey(data.url)
        .placeholder(R.drawable.three)
        .error(R.drawable.three)
        .fallback(R.drawable.three)
        .diskCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()
}

private fun getHeptagonPath(
    centerX: Float,
    centerY: Float,
    radius: Float,
    padding: Float = 0f
): Path {
    // Calculate Vertices
    val vertices = mutableListOf<Offset>()
    repeat(7) { index ->
        val angle = PI / 2 + 2 * PI / 7 * index
        val x = centerX + (radius - padding) * cos(angle).toFloat()
        val y = centerY + (radius - padding) * sin(angle).toFloat()
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
    HeptagonWithImage(100f, 100f, 100f, Color.Green, DataModel("", "", R.drawable.fourteen))
}