package com.davidgarcia.pokedex.ui.components

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import com.davidgarcia.pokedex.ui.utils.toBoldAnnotatedString


@Composable
fun BoldAnnotatedText(
    modifier: Modifier = Modifier,
    @StringRes textRes: Int,
    @StringRes boldRes: Int,
    style: TextStyle = MaterialTheme.typography.titleMedium
) {
    val fullText = stringResource(textRes)
    val boldPart = stringResource(boldRes)
    val annotated: AnnotatedString = remember(fullText, boldPart) {
        fullText.toBoldAnnotatedString(boldPart)
    }

    Text(
        text = annotated,
        style = style,
        modifier = modifier
    )
}
