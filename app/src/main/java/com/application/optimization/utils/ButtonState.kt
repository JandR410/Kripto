package com.application.optimization.utils

data class ButtonState(
    val text: String,
    val onButtonClicked: () -> Unit
)