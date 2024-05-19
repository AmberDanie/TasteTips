package pet.project.tastetips.data

import kotlinx.coroutines.flow.StateFlow
import pet.project.tastetips.model.RefrigeratorState

data class TasteTipsState(
    val loginShellIsOpen: Boolean = false,
    val registerShellIsOpen: Boolean = false,
    val isLoading: Boolean = true,
    val isChoosable: Boolean = false,
    val searchText: String = "",
    val chosenRecipe: MealModel? = null,
    val dialogName: String = "",
    val dialogDate: String = "",
    val dialogIconIndex: Int = 0,
    val currentScreenIndex: Int = 0,
    val refrigeratorItems: StateFlow<RefrigeratorState>
)