package com.example.core.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.optimization.base.screen.ScreenAction
import com.application.optimization.base.screen.ScreenEvent
import com.application.optimization.base.screen.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : ScreenState, Action : ScreenAction, Event : ScreenEvent>(
    initialState: State
) : ViewModel(), StateHandler<State> by StateHandlerImpl(initialState) {

    abstract fun handleScreenActions(action: Action)

    private val _eventsFlow = MutableSharedFlow<Event>()
    val eventsFlow: Flow<Event>
        get() = _eventsFlow


    protected fun sendEvent(event: Event) {
        viewModelScope.launch {
            _eventsFlow.emit(event)
        }
    }
}



