package pet.project.tastetips.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pet.project.tastetips.NavGraph
import pet.project.tastetips.R
import pet.project.tastetips.model.TasteTipsViewModel
import pet.project.tastetips.ui.theme.TasteTipsTheme

@Composable
fun FavoriteScreen(navController: NavController,
                   viewModel: TasteTipsViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val dishesState by uiState.favouriteDishes.collectAsState()
    val dishes = dishesState.dishesList
    Column() {
        Card(modifier = Modifier,
            shape = RectangleShape
        ) {
            Text(
                "Favourite dishes",
                fontSize = 36.sp,
                modifier = Modifier.fillMaxWidth()
                    .background(colorResource(id = R.color.teal_700))
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
        FoodColumn(mealData = dishes, navigateToPositionInfo = {
            viewModel.openRecipeInfo(it)
            navController.navigate(
                route = NavGraph.RecipeInfo.name
            )
        },
            onLikeClick = {})
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    TasteTipsTheme {
        FavoriteScreen(rememberNavController(), viewModel())
    }
}