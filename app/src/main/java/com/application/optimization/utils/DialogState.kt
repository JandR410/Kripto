package com.application.optimization.utils

data class DialogState(
    val title: String,
    val message: String,
    val firstButton: ButtonState?,
    val secondButton: ButtonState?
)