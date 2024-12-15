package org.stc.marvel.custom

import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.tan

class CustomTextDrawer(private val slantAngle: Float = 15f) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(
        Path().apply {
            val slantOffset = tan(Math.toRadians(slantAngle.toDouble())).toFloat() * size.height
            moveTo(slantOffset, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width - slantOffset, size.height)
            lineTo(0f, size.height)
            close()
        }
    )
}