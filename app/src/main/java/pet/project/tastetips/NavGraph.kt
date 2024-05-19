package pet.project.tastetips

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pet.project.tastetips.data.getNavigationItems
import pet.project.tastetips.model.TasteTipsViewModel
import pet.project.tastetips.ui.screens.AuthorizationScreen
import pet.project.tastetips.ui.screens.FavoriteScreen
import pet.project.tastetips.ui.screens.SettingsScreen
import pet.project.tastetips.ui.screens.recipes.RecipeInfoScreen
import pet.project.tastetips.ui.screens.recipes.RecipesScreen
import pet.project.tastetips.ui.screens.refrigerator.RefrigeratorScreen
import pet.project.tastetips.ui.theme.TasteTipsTheme

enum class NavGraph(@StringRes val title: Int) {
    Authorization(title = R.string.authorization),
    Refrigerator(title = R.string.refrigerator),
    Recipes(title = R.string.recipes),
    RecipeInfo(title = R.string.recipe_info),
    Favorite(title = R.string.favorite),
    Settings(title = R.string.settings)
}

@Composable
fun TasteTipsApp(
    viewModel: TasteTipsViewModel = viewModel(factory = TasteTipsViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {

    val uiState by viewModel.uiState.collectAsState()

    val currentScreenIndex = uiState.currentScreenIndex

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = NavGraph.valueOf(
        backStackEntry?.destination?.route ?:
        NavGraph.Authorization.name
    )

    Scaffold(
        bottomBar = {
            if (currentScreen.name != NavGraph.Authorization.name &&
                currentScreen.name != NavGraph.RecipeInfo.name) {
                TasteTipsBottomBar(navController = navController,
                    currentScreenIndex
                ) { viewModel.updateCurrentScreenIndex(it) }
            }
        }
    ) { innerPadding ->
        TasteTipsNavHost(navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasteTipsBottomBar(navController: NavHostController,
                       selectedScreenIndex: Int,
                       onNavigationBarClick: (Int) -> Unit) {
    val items = getNavigationItems()
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedScreenIndex == index,
                onClick = {
                    onNavigationBarClick(index)
                    navController.navigate(item.title)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            /* TODO */
                        }
                    ) {
                        Icon(
                            imageVector = if (index==selectedScreenIndex)
                                item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun TasteTipsNavHost(navController: NavHostController,
                     viewModel: TasteTipsViewModel,
                     modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavGraph.Authorization.name,
        modifier = modifier,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        composable(route = NavGraph.Authorization.name) {
            AuthorizationScreen(navController, viewModel)
        }
        composable(route = NavGraph.Refrigerator.name) {
            RefrigeratorScreen(viewModel)
        }
        composable(route = NavGraph.Recipes.name) {
            RecipesScreen(navController, viewModel)
        }
        composable(route = NavGraph.RecipeInfo.name) {
            RecipeInfoScreen(navController, viewModel)
        }
        composable(route = NavGraph.Favorite.name) {
            FavoriteScreen(navController, viewModel)
        }
        composable(route = NavGraph.Settings.name) {
            SettingsScreen(navController, viewModel)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterPreview() {
    TasteTipsTheme {
        AuthorizationScreen(rememberNavController(), viewModel())
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun RefrigeratorPreview() {
    TasteTipsTheme {
        RefrigeratorScreen(viewModel())
    }
}