package com.application.optimization.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.optimization.domain.AppInfo
import com.application.optimization.presentation.home.HomeAction
import com.application.optimization.ui.theme.Cyan
import com.application.optimization.ui.theme.White

@Composable
fun ListApp(
    modifier: Modifier,
    action: (HomeAction) -> Unit,
    listAppInfo: List<AppInfo>
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        items(listAppInfo) { appInfo ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(14.dp))
                    .border(
                        width = 2.dp,
                        color = Cyan,
                        shape = RoundedCornerShape(14.dp)

                    ),
                onClick = { action(HomeAction.ShowAppInfo(appInfo)) },
            ) {
                Column(
                    modifier = Modifier
                        .background(color = White)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = appInfo.name.uppercase(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Memoria: ${appInfo.memoryConsumption} MB",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Usuarios: ${appInfo.userCount}",
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "CPU: ${appInfo.cpuUsage}%",
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}