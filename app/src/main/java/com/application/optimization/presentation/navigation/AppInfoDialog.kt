package com.application.optimization.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.optimization.domain.AppInfo

@Composable
fun AppInfoDialog(appInfo: AppInfo, onClose: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onClose() },
        confirmButton = {
            Button(onClick = { onClose() }) {
                Text(text = "Cerrar", modifier = Modifier.padding(8.dp))
            }
        },
        title = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)) {
                Text(
                    text = appInfo.name.uppercase(),
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column {
                Text(text = "Memoria Consumida: ${appInfo.memoryConsumption} MB", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "CPU en uso: ${appInfo.cpuUsage} %", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Usuarios Activos: ${appInfo.userCount}", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Criticidad: ${appInfo.criticality}/10", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Almacenamiento: ${appInfo.storageUsage} GB", fontSize = 14.sp)
            }
        }
    )
}
