package com.application.optimization.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.application.optimization.ui.theme.Cyan
import com.application.optimization.ui.theme.DarkGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Cyan,
                modifier = Modifier.padding(16.dp)
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = DarkGray
        )
    )
}
