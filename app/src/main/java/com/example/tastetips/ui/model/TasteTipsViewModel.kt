package com.example.tastetips.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastetips.ui.data.RefrigeratorItem
import com.example.tastetips.ui.data.refrigeratorItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    private fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
        clear()
        addAll(newList)
    }

    fun getRefrigeratorItems(
        refrigeratorItemsList: SnapshotStateList<RefrigeratorItem>
    ) {
        refrigeratorItemsList.swapList(refrigeratorItems)
    }

    fun updateRefrigeratorItems(
        refrigeratorItemsList: SnapshotStateList<RefrigeratorItem>
    ) {
        refrigeratorItems.add(
            RefrigeratorItem(
                Icons.Outlined.Favorite,
                "Like",
                2
            )
        )
        refrigeratorItemsList.swapList(refrigeratorItems)
    }

    init {
        viewModelScope.launch {
            delay(1000)
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false
                )
            }
        }
    }

}