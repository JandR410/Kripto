package com.application.optimization.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.application.optimization.ui.theme.Cyan
import com.application.optimization.ui.theme.DarkGray
import com.application.optimization.ui.theme.White

@Composable
fun BottomNavigationComponent(
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    val mutableList = mutableListOf(
        "Home" to Icons.Default.Home,
        "Optimización" to Icons.Filled.Notifications,
        "Gráfica" to Icons.Filled.Info,
    )

    NavigationBar(containerColor = DarkGray) {
        mutableList.forEach { item ->
            NavigationBarItem(
                selected = item.first == selectedItem,
                onClick = { onItemSelected(item.first) },
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            imageVector = item.second,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp),
                            tint = if (item.first == selectedItem) Cyan else White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.first,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (item.first == selectedItem) Cyan else White
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Cyan,
                    selectedTextColor = Cyan,
                    unselectedIconColor = White,
                    unselectedTextColor = White,
                    indicatorColor = DarkGray
                )
            )
        }
    }
}