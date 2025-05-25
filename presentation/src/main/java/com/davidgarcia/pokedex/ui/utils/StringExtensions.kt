package com.davidgarcia.pokedex.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.core.graphics.toColorInt

fun String.toBoldAnnotatedString(boldPart: String): AnnotatedString = buildAnnotatedString {
    append(this@toBoldAnnotatedString)
    val start = this@toBoldAnnotatedString.indexOf(boldPart)
    if (start >= 0) {
        addStyle(
            style = SpanStyle(fontWeight = FontWeight.Bold),
            start = start,
            end = start + boldPart.length
        )
    }
}

fun String.toColor(): Color =
    try {
        Color(this.toColorInt())
    } catch (e: Exception) {
        Color.Transparent
    }