package com.exa.pelis.ui.common

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.exa.pelis.R

@Composable
fun Button(modifier: Modifier = Modifier, onClick: () -> Unit, text: String) {
    androidx.compose.material3.Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary))
    ) {
        BodyText(
            text = text,
            color = colorResource(id = R.color.on_primary)
        )

    }

}