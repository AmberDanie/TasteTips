package pet.project.tastetips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import pet.project.tastetips.model.TasteTipsViewModel
import pet.project.tastetips.network.AuthUiClient
import pet.project.tastetips.ui.screens.AuthorizationScreen
import pet.project.tastetips.ui.screens.FavoriteScreen
import pet.project.tastetips.ui.screens.SettingsScreen
import pet.project.tastetips.ui.screens.recipes.RecipeInfoScreen
import pet.project.tastetips.ui.screens.recipes.RecipesScreen
import pet.project.tastetips.ui.screens.refrigerator.RefrigeratorScreen
import pet.project.tastetips.ui.theme.TasteTipsTheme

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        AuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private val viewModel: TasteTipsViewModel by viewModels(factoryProducer = { TasteTipsViewModel.Factory })

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition{viewModel.uiState.value.isLoading}

        setContent {
            TasteTipsTheme {
                val navController = rememberNavController()
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
                    NavHost(
                        navController = navController,
                        startDestination = NavGraph.Authorization.name,
                        enterTransition = { fadeIn() },
                        exitTransition = { fadeOut() },
                        popEnterTransition = { fadeIn() },
                        popExitTransition = { fadeOut() },
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(NavGraph.Authorization.name) {
                            val signInState by viewModel.signInState.collectAsStateWithLifecycle()
                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = signInState.isSignInSuccessful) {
                                if (signInState.isSignInSuccessful) {
                                    navController.navigate(
                                        route = NavGraph.Refrigerator.name
                                    ) {
                                        popUpTo(
                                            NavGraph.Authorization.name
                                        ) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }

                            AuthorizationScreen(navController = navController,
                                viewModel = viewModel,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
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
            }
        }
    }
}