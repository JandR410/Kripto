package com.example.core.state

import com.application.optimization.base.BaseScreenState
import com.application.optimization.base.screen.ScreenState
import com.application.optimization.utils.DialogState
import kotlinx.coroutines.flow.StateFlow

interface StateHandler<S : ScreenState> {
    val screenState: StateFlow<BaseScreenState<S>>
    val state: S

    fun setState(newState: S)

    fun showLoading()
    fun hideLoading()

    fun showDialog(dialog: DialogState)
    fun dismissDialog()
}