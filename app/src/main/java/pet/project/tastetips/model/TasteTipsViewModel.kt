package pet.project.tastetips.model

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pet.project.tastetips.TasteTipsApplication
import pet.project.tastetips.data.MealModel
import pet.project.tastetips.data.RecipeData
import pet.project.tastetips.data.RefrigeratorIcon
import pet.project.tastetips.data.RefrigeratorItem
import pet.project.tastetips.data.SignInResult
import pet.project.tastetips.data.TasteTipsState
import pet.project.tastetips.data.refrigeratorIcons
import pet.project.tastetips.data.states.SignInState
import pet.project.tastetips.network.TasteTipsRepository
import java.io.IOException

sealed interface NetworkState {
    data class Success(val data: RecipeData) : NetworkState
    data object Error : NetworkState
    data object Loading : NetworkState
}

data class RefrigeratorState(val itemList: List<RefrigeratorItem> = listOf())

class TasteTipsViewModel(private val tasteTipsRepository: TasteTipsRepository): ViewModel() {
    private val _uiState = MutableStateFlow(TasteTipsState(
        refrigeratorItems = getRefrigeratorItems())
    )
    val uiState: StateFlow<TasteTipsState> = _uiState.asStateFlow()

    private val _signInState = MutableStateFlow(SignInState())
    val signInState: StateFlow<SignInState> = _signInState.asStateFlow()

    var networkState: NetworkState by mutableStateOf(NetworkState.Loading)
        private set

    private var dialogIconIndex by mutableIntStateOf(0)

    fun onSignInResult(result: SignInResult) {
        _signInState.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetSignInState() {
        _signInState.update {
            SignInState()
        }
    }

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

    fun updateCurrentScreenIndex(chosenIndex: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                currentScreenIndex = chosenIndex
            )
        }
    }

    private fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
        clear()
        addAll(newList)
    }

    fun getRefrigeratorItems(): StateFlow<RefrigeratorState> {
        return tasteTipsRepository.getAllRefrigeratorItemsStream().map{RefrigeratorState(it)}.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            RefrigeratorState()
        )
    }

    private suspend fun addRefrigeratorItem(
        refrigeratorItem: RefrigeratorItem
    ) {
        tasteTipsRepository.insert(refrigeratorItem)
    }

    suspend fun removeRefrigeratorItem(refrigeratorItem: RefrigeratorItem) {
        tasteTipsRepository.delete(refrigeratorItem)
    }

    fun getRefrigeratorIcons(
        refrigeratorIconsList: SnapshotStateList<RefrigeratorIcon>
    ) {
        refrigeratorIconsList.swapList(refrigeratorIcons)
    }

    fun updateDialogNameFieldText(newText: String) {
        _uiState.update {
            it.copy(
                dialogName = newText
            )
        }
    }

    fun updateDialogDatedFieldText(newDate: String) {
        _uiState.update {
            it.copy(
                dialogDate = newDate
            )
        }
    }

    fun dialogConfirmation() {
        val refrigeratorItem = RefrigeratorItem(
            positionIcon = dialogIconIndex,
            positionName = _uiState.value.dialogName,
            positionExpirationDate = _uiState.value.dialogDate)
        viewModelScope.launch {
            addRefrigeratorItem(refrigeratorItem)
        }
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

    fun openRecipeInfo(position: MealModel) {
        _uiState.update {
            it.copy(
                chosenRecipe = position
            )
        }
    }

    private fun getFoodList() {
        viewModelScope.launch {
            networkState = NetworkState.Loading
            networkState = try {
                val result = tasteTipsRepository.getFoodList()
                NetworkState.Success(
                    result
                )
            } catch (e: IOException) {
                NetworkState.Error
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