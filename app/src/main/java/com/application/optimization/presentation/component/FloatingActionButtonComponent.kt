package com.application.optimization.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.application.optimization.ui.theme.Cyan

@Composable
fun FloatingActionButtonComponent(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Default.AddCircle,
        contentDescription = "Agregar",
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
            .size(40.dp),
        tint = Cyan
    )
}
