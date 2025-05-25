package com.davidgarcia.pokedex.ui.utils

import android.graphics.Bitmap
import coil.size.Size
import coil.transform.Transformation

class TrimTransformation : Transformation {
    override val cacheKey: String = "TrimTransformation"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val width = input.width
        val height = input.height
        val pixels = IntArray(width * height)
        input.getPixels(pixels, 0, width, 0, 0, width, height)

        var left = width
        var top = height
        var right = 0
        var bottom = 0

        for (y in 0 until height) {
            for (x in 0 until width) {
                val px = pixels[y * width + x]
                if (px != 0xFFFFFFFF.toInt() && (px ushr 24) != 0) {
                    left = minOf(left, x)
                    right = maxOf(right, x)
                    top = minOf(top, y)
                    bottom = maxOf(bottom, y)
                }
            }
        }

        if (left > right || top > bottom) return input

        val cropW = right - left + 1
        val cropH = bottom - top + 1
        val cropped = Bitmap.createBitmap(input, left, top, cropW, cropH)
        if (cropped != input) input.recycle()
        return cropped
    }
}
