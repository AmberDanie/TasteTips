package pet.project.tastetips.ui.screens.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pet.project.tastetips.NavGraph
import pet.project.tastetips.R
import pet.project.tastetips.model.NetworkState
import pet.project.tastetips.model.TasteTipsViewModel
import pet.project.tastetips.ui.screens.FoodColumn

@Composable
fun RecipesScreen(navController: NavController, viewModel: TasteTipsViewModel) {
    val recipesState = viewModel.networkState
    Column() {
        RecipesSearchBar()
        when (recipesState) {
            is NetworkState.Success ->
                FoodColumn(mealData = recipesState.data.meals,
                    navigateToPositionInfo = {
                        viewModel.openRecipeInfo(it)
                        navController.navigate(
                            route = NavGraph.RecipeInfo.name
                        )
                    },
                    onLikeClick = {
                        viewModel.onLikeClick(it)
                    })
            is NetworkState.Error -> {}
            is NetworkState.Loading -> {}
            else -> {}
        }
    }
}

@Composable
fun RecipesSearchBar(modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = {

        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = colorResource(id = R.color.teal_700),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}