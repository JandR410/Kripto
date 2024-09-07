package com.application.optimization.presentation.home

import com.application.optimization.base.screen.ScreenAction
import com.application.optimization.domain.AppInfo

sealed class HomeAction: ScreenAction {
    data class Add(val fieldValues: List<String>) : HomeAction()
    data object Home : HomeAction()
    data class ShowAppInfo(val appInfo: AppInfo) : HomeAction()
    data object ShowAppInfoClose : HomeAction()
    data class OptimizeAppUseCase(val appInfo: List<AppInfo>, val selectedCriteria: String) : HomeAction()
    data object ToggleOptimos : HomeAction()
}