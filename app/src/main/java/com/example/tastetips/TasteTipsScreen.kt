package com.example.tastetips

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tastetips.ui.RegisterScreen
import com.example.tastetips.ui.TasteTipsViewModel
import com.example.tastetips.ui.WelcomeScreen
import com.example.tastetips.ui.theme.TasteTipsTheme

enum class TasteTipsScreen(@StringRes val title: Int) {
    Welcome(title = R.string.welcome),
    Register(title = R.string.register),
    Login(title = R.string.login_screen),
    Home(title = R.string.home_screen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasteTipsApp(
    viewModel: TasteTipsViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = TasteTipsScreen.valueOf(
        backStackEntry?.destination?.route ?: TasteTipsScreen.Welcome.name
    )

    val canNavigateBack = navController.previousBackStackEntry != null

    Scaffold(
        topBar = {}
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TasteTipsScreen.Welcome.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = TasteTipsScreen.Welcome.name) {
                WelcomeScreen(
                    onStartButtonClicked = {
                        navController.navigate(route = TasteTipsScreen.Login.name)
                    }
                )
            }
            composable(route = TasteTipsScreen.Login.name) {
                RegisterScreen(viewModel)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomePreview() {
    TasteTipsTheme {
        WelcomeScreen {
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterPreview() {
    TasteTipsTheme {
        RegisterScreen(viewModel())
    }
}