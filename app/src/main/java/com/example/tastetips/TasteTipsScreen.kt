package com.example.tastetips

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tastetips.ui.AuthorizationScreen
import com.example.tastetips.ui.FavoriteScreen
import com.example.tastetips.ui.RecipesScreen
import com.example.tastetips.ui.SettingsScreen
import com.example.tastetips.ui.model.TasteTipsViewModel
import com.example.tastetips.ui.model.getNavigationItems
import com.example.tastetips.ui.refrigerator.RefrigeratorScreen
import com.example.tastetips.ui.theme.TasteTipsTheme

enum class TasteTipsScreen(@StringRes val title: Int) {
    Authorization(title = R.string.authorization),
    Refrigerator(title = R.string.refrigerator),
    Recipes(title = R.string.recipes),
    Favorite(title = R.string.favorite),
    Settings(title = R.string.settings)
}

@Composable
fun TasteTipsApp(
    viewModel: TasteTipsViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = TasteTipsScreen.valueOf(
        backStackEntry?.destination?.route ?:
        TasteTipsScreen.Authorization.name
    )

    Scaffold(
        bottomBar = {
            if (currentScreen.name != TasteTipsScreen.Authorization.name) {
                TasteTipsBottomBar(navController = navController)
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
fun TasteTipsBottomBar(navController: NavHostController) {
    val items = getNavigationItems()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
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
                            imageVector = if (index==selectedItemIndex)
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
        startDestination = TasteTipsScreen.Authorization.name,
        modifier = modifier,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        composable(route = TasteTipsScreen.Authorization.name) {
            AuthorizationScreen(navController, viewModel)
        }
        composable(route = TasteTipsScreen.Refrigerator.name) {
            RefrigeratorScreen(viewModel)
        }
        composable(route = TasteTipsScreen.Recipes.name) {
            RecipesScreen(navController, viewModel)
        }
        composable(route = TasteTipsScreen.Favorite.name) {
            FavoriteScreen(navController, viewModel)
        }
        composable(route = TasteTipsScreen.Settings.name) {
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