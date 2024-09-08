package com.application.optimization.presentation.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.application.optimization.data.repository.AppRepository
import com.application.optimization.domain.AppInfo
import com.application.optimization.usecase.OptimizationColor
import com.application.optimization.usecase.OptimizeAppsUseCase
import com.application.optimization.utils.ButtonState
import com.application.optimization.utils.DialogState
import com.example.core.state.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val optimizeAppsUseCase: OptimizeAppsUseCase
) : BaseViewModel<HomeState, HomeAction, HomeEvent>(initialState = HomeState.buildInitialState()) {

    init {
        loadApp()
    }

    override fun handleScreenActions(action: HomeAction) {
        when (action) {
            is HomeAction.Add -> addApplication(action.fieldValues)
            HomeAction.Home -> loadApp()
            is HomeAction.ShowAppInfo -> showAppInfo(action.appInfo)
            HomeAction.ShowAppInfoClose -> showAppInfoClose()
            is HomeAction.OptimizeAppUseCase -> executeUseCase(
                action.appInfo,
                action.selectedCriteria
            )

            is HomeAction.ToggleOptimos -> toggleOptimos()
            is HomeAction.ExportExcel -> exportAppsToExcel(action.context)
        }
    }

    private fun executeUseCase(appInfo: List<AppInfo>, selectedCriteria: String) {
        val result = optimizeAppsUseCase.execute(appInfo, selectedCriteria)
        val filteredResult = result.filter {
            it.color == OptimizationColor.RED || it.color == OptimizationColor.YELLOW
        }
        setState(state.copy(listOptimization = filteredResult, showOptimos = false))
    }

    private fun toggleOptimos() {
        val shouldShowOptimos = !state.showOptimos
        val result = if (shouldShowOptimos) {
            optimizeAppsUseCase.execute(
                state.listAppInfo,
                "criterio"
            )
        } else {
            state.listOptimization.filter {
                it.color == OptimizationColor.RED || it.color == OptimizationColor.YELLOW
            }
        }
        setState(state.copy(listOptimization = result, showOptimos = shouldShowOptimos))
    }

    private fun showAppInfoClose() {
        setState(state.copy(appInfo = null))
    }

    private fun showAppInfo(appInfo: AppInfo) {
        setState(state.copy(appInfo = appInfo))
    }

    fun loadApp() {
        viewModelScope.launch {
            val appList = appRepository.getApps()
            setState(state.copy(listAppInfo = appList))
        }
    }

    private fun addApplication(fieldValues: List<String>) {
        val app = AppInfo(
            id = 0,
            name = fieldValues[0],
            usageFrequency = fieldValues[1].toIntOrNull() ?: 0,
            memoryConsumption = fieldValues[2].toDoubleOrNull() ?: 0.0,
            cpuUsage = fieldValues[3].toDoubleOrNull() ?: 0.0,
            lastUpdated = fieldValues[4],
            criticality = fieldValues[5].toIntOrNull() ?: 0,
            userCount = fieldValues[6].toIntOrNull() ?: 0,
            storageUsage = fieldValues[7].toDoubleOrNull() ?: 0.0,
        )
        viewModelScope.launch {
            appRepository.insertApp(app)
        }
        showDialog(
            DialogState(
                title = "Aplicacion Agregada",
                message = "Su aplicación fue agregada con éxito",
                firstButton = ButtonState("Aceptar") { dismissDialog() },
                secondButton = null,
            )
        )
    }

    private fun exportAppsToExcel(context: Context) {
        viewModelScope.launch {
            val apps = state.listAppInfo
            optimizeAppsUseCase.exportAppsToExcel(context, apps)
        }
    }
}

