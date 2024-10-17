package com.exa.pelis.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.exa.pelis.R

@Composable
fun Title(text: String, modifier: Modifier = Modifier, color: Color = colorResource(R.color.on_surface_text)) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = color,
        modifier = modifier
    )
}

@Composable
fun BodyText(text: String, modifier: Modifier = Modifier, color: Color = colorResource(R.color.on_surface_text), fontSize: TextUnit = 16.sp ) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        modifier = modifier,
        fontSize = fontSize,
    )
}