package com.exa.pelis.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

@Composable
fun RetryButton(error: String, onClick: () -> Unit) {
    return Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BodyText(text = error, color = colorResource(R.color.error))
        Button(text = stringResource(id = R.string.retry), onClick = onClick)
    }
}