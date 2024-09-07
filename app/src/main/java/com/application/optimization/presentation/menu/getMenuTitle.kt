package com.application.optimization.presentation.menu

fun getMenuTitle(currentContent: MenuApp): String {
    return when (currentContent) {
        MenuApp.Home -> MenuLabels.HOME
        MenuApp.Optimize -> MenuLabels.OPTIMIZE
        MenuApp.Graph -> MenuLabels.GRAPH
        MenuApp.Add -> MenuLabels.ADD
    }
}
