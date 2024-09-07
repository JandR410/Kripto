// File: NavigationApp.kt
package com.application.optimization.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.application.optimization.base.BaseScreen
import com.application.optimization.presentation.component.BottomNavigationComponent
import com.application.optimization.presentation.component.FloatingActionButtonComponent
import com.application.optimization.presentation.component.TopBarComponent
import com.application.optimization.presentation.home.HomeViewModel
import com.application.optimization.presentation.menu.MenuApp
import com.application.optimization.presentation.menu.MenuLabels
import com.application.optimization.presentation.menu.getMenuTitle

@Composable
fun NavigationApp() {
    var selectedItem by remember { mutableStateOf(MenuLabels.HOME) }
    var currentContent by remember { mutableStateOf<MenuApp>(MenuApp.Home) }
    var showDialog by remember { mutableStateOf(false) }
    val viewModel: HomeViewModel = hiltViewModel()
    val screenState = viewModel.screenState.collectAsState().value

    val title = getMenuTitle(currentContent)

    BaseScreen(baseScreenState = screenState) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBarComponent(title) },
            floatingActionButton = {
                FloatingActionButtonComponent {
                    currentContent = MenuApp.Add
                }
            },
            bottomBar = {
                BottomNavigationComponent(
                    selectedItem = selectedItem,
                    onItemSelected = { item ->
                        selectedItem = item
                        currentContent = when (item) {
                            MenuLabels.HOME -> MenuApp.Home
                            MenuLabels.OPTIMIZE -> MenuApp.Optimize
                            MenuLabels.GRAPH -> MenuApp.Graph
                            MenuLabels.ADD -> MenuApp.Add
                            else -> MenuApp.Home
                        }
                    }
                )
            }
        ) { paddingValues ->
            if (showDialog) {
                viewModel.state.appInfo?.let {
                    AppInfoDialog(it) { showDialog = false }
                }
            }

            when (currentContent) {
                MenuApp.Home -> LoadHomeContent(viewModel, paddingValues, showDialog) { showDialog = it }
                MenuApp.Optimize -> LoadOptimizeContent(viewModel, viewModel.state.listAppInfo,viewModel.state.listOptimization,paddingValues)
                MenuApp.Graph -> LoadGraphContent(viewModel, paddingValues)
                MenuApp.Add -> LoadAddContent(viewModel, paddingValues)
            }
        }
    }
}