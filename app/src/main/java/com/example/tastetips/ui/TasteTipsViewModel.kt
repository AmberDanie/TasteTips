package com.example.tastetips.ui

import androidx.lifecycle.ViewModel
import com.example.tastetips.ui.model.TasteTipsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TasteTipsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(TasteTipsState())
    val uiState: StateFlow<TasteTipsState> = _uiState.asStateFlow()

    fun updateRegisterSheet() {
        _uiState.update { currentState ->
            currentState.copy(
                registerShellIsOpen = !currentState.registerShellIsOpen
            )
        }
    }

    fun updateLoginSheet() {
        _uiState.update { currentState ->
            currentState.copy(
                loginShellIsOpen = !currentState.loginShellIsOpen
            )
        }
    }
}