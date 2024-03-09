package com.example.tastetips.ui.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastetips.ui.data.RefrigeratorIcon
import com.example.tastetips.ui.data.RefrigeratorItem
import com.example.tastetips.ui.data.refrigeratorIcons
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

    var text by mutableStateOf("")

    var date by mutableStateOf("")

    private var currentIconIndex by mutableIntStateOf(0)

    fun updateCurrentIcon(index: Int) {
        refrigeratorIcons[currentIconIndex].isChosen = false
        currentIconIndex = index
        refrigeratorIcons[currentIconIndex].isChosen = true
    }

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

    private fun updateRefrigeratorItems(
        refrigeratorItemsList: SnapshotStateList<RefrigeratorItem>
    ) {
        refrigeratorItems.add(
            RefrigeratorItem(
                positionIcon = refrigeratorIcons[currentIconIndex].filledIcon,
                positionName = text,
                positionIndicator = 2,
                positionExpirationDate = date
            )
        )
        refrigeratorItemsList.swapList(refrigeratorItems)
    }

    fun removeRefrigeratorItem(refrigeratorItem: RefrigeratorItem,
                               refrigeratorItemsList: SnapshotStateList<RefrigeratorItem>) {
        refrigeratorItems.remove(refrigeratorItem)
        refrigeratorItemsList.swapList(refrigeratorItems)
    }

    fun getRefrigeratorIcons(
        refrigeratorIconsList: SnapshotStateList<RefrigeratorIcon>
    ) {
        refrigeratorIconsList.swapList(refrigeratorIcons)
    }

    fun updateDialogNameFieldText(newText: String) {
        text = newText
    }

    fun updateDialogDatedFieldText(newDate: String) {
        date = newDate
    }

    fun dialogConfirmation(
        refrigeratorItemsList: SnapshotStateList<RefrigeratorItem>
    ) {
        updateRefrigeratorItems(
            refrigeratorItemsList
        )
    }

    fun updateDialogState() {
        _uiState.update {
            it.copy(
                isChoosable = !it.isChoosable
            )
        }
        updateCurrentIcon(0)
        updateDialogDatedFieldText("")
        updateDialogNameFieldText("")
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