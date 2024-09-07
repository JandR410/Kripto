package com.application.optimization.presentation.home

import com.application.optimization.base.screen.ScreenState
import com.application.optimization.domain.AppInfo
import com.application.optimization.usecase.OptimizationRecommendation

data class HomeState(
    val listAppInfo: List<AppInfo> = emptyList(),
    val listOptimization: List<OptimizationRecommendation> = emptyList(),
    val appInfo: AppInfo? = null,
    val showOptimos: Boolean = false
): ScreenState {
    companion object {
        fun buildInitialState() = HomeState()
    }
}