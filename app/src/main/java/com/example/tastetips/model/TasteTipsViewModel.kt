package com.example.tastetips.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tastetips.TasteTipsApplication
import com.example.tastetips.data.RefrigeratorIcon
import com.example.tastetips.data.RefrigeratorItem
import com.example.tastetips.data.TasteTipsRepository
import com.example.tastetips.data.refrigeratorIcons
import com.example.tastetips.data.refrigeratorItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface RecipesUiState {
    data class Success(val data: RecipeData) : RecipesUiState
    data object Error : RecipesUiState
    data object Loading : RecipesUiState
}

class TasteTipsViewModel(private val tasteTipsRepository: TasteTipsRepository): ViewModel() {
    private val _uiState = MutableStateFlow(TasteTipsState())
    val uiState: StateFlow<TasteTipsState> = _uiState.asStateFlow()

    var recipesUiState: RecipesUiState by mutableStateOf(RecipesUiState.Loading)
        private set

    var dialogName by mutableStateOf("")

    var dialogDate by mutableStateOf("")

    private var dialogIconIndex by mutableIntStateOf(0)

    fun updateCurrentIcon(index: Int) {
        refrigeratorIcons[dialogIconIndex].isChosen = false
        dialogIconIndex = index
        refrigeratorIcons[dialogIconIndex].isChosen = true
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
                positionIcon = refrigeratorIcons[dialogIconIndex].filledIcon,
                positionName = dialogName,
                positionIndicator = 2,
                positionExpirationDate = dialogDate
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
        dialogName = newText
    }

    fun updateDialogDatedFieldText(newDate: String) {
        dialogDate = newDate
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

    private fun getFoodList() {
        viewModelScope.launch {
            recipesUiState = RecipesUiState.Loading
            recipesUiState = try {
                val result = tasteTipsRepository.getFoodList()
                RecipesUiState.Success(
                    result
                )
            } catch (e: IOException) {
                RecipesUiState.Error
            }
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false
                )
            }
        }
    }

    init {
        getFoodList()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TasteTipsApplication)
                val tasteTipsRepository = application.container.tasteTipsRepository
                TasteTipsViewModel(tasteTipsRepository = tasteTipsRepository)
            }
        }
    }

}