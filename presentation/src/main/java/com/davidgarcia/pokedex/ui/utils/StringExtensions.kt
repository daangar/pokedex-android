package com.davidgarcia.pokedex.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight

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
