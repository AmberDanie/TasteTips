package pet.project.tastetips.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pet.project.tastetips.model.TasteTipsViewModel
import pet.project.tastetips.ui.theme.TasteTipsTheme

@Composable
fun FavoriteScreen(navController: NavController, viewModel: TasteTipsViewModel) {

}

@Preview
@Composable
fun FavoriteScreenPreview() {
    TasteTipsTheme {
        FavoriteScreen(rememberNavController(), viewModel())
    }
}