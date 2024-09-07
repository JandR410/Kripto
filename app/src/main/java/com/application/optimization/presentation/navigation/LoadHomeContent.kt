package com.application.optimization.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.application.optimization.domain.AppInfo
import com.application.optimization.list.ListApp
import com.application.optimization.presentation.addApp.AddApp
import com.application.optimization.presentation.graph.OptimizeAppGraphs
import com.application.optimization.presentation.home.HomeAction
import com.application.optimization.presentation.home.HomeViewModel
import com.application.optimization.presentation.optimizeApp.OptimizeAppScreen
import com.application.optimization.usecase.OptimizationRecommendation

@Composable
fun LoadHomeContent(
    viewModel: HomeViewModel,
    paddingValues: PaddingValues,
    showDialog: Boolean,
    onDialogToggle: (Boolean) -> Unit
) {
    viewModel.loadApp()
    var showDialogs by remember { mutableStateOf(showDialog) }

    ListApp(
        modifier = Modifier.padding(paddingValues),
        action = {
            viewModel.handleScreenActions(it)
            if (it is HomeAction.ShowAppInfo) {
                showDialogs = true
                onDialogToggle(true)
            }
        },
        listAppInfo = viewModel.state.listAppInfo
    )
}

@Composable
fun LoadOptimizeContent(viewModel: HomeViewModel, listAppInfo: List<AppInfo>, listOptimization: List<OptimizationRecommendation>, paddingValues: PaddingValues) {
    OptimizeAppScreen(
        modifier = Modifier.padding(paddingValues),
        listAppInfo = listAppInfo,
        listOptimization = listOptimization,
        action = viewModel::handleScreenActions
    )
}

@Composable
fun LoadGraphContent(viewModel: HomeViewModel, paddingValues: PaddingValues) {
    OptimizeAppGraphs(
        modifier = Modifier.padding(paddingValues),
        listAppInfo = viewModel.state.listAppInfo,
        listOptimization = viewModel.state.listOptimization,
        action = viewModel::handleScreenActions
    )
}

@Composable
fun LoadAddContent(viewModel: HomeViewModel, paddingValues: PaddingValues) {
    AddApp(
        modifier = Modifier.padding(paddingValues),
        actionHandler = viewModel::handleScreenActions
    )
}
