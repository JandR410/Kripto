package com.application.optimization.presentation.menu

sealed class MenuApp {
    data object Add: MenuApp()
    data object Home: MenuApp()
    data object Optimize: MenuApp()
    data object Graph: MenuApp()
}
