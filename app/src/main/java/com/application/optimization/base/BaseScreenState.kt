package com.application.optimization.base

import com.application.optimization.utils.DialogState

data class BaseScreenState<T>(
    val state: T,
    val loading: Boolean = false,
    val dialog: DialogState? = null,
)