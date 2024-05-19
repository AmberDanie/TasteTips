package pet.project.tastetips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import pet.project.tastetips.model.TasteTipsViewModel
import pet.project.tastetips.ui.theme.TasteTipsTheme

class MainActivity : ComponentActivity() {

    private val viewModel: TasteTipsViewModel by viewModels(factoryProducer = { TasteTipsViewModel.Factory })

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition{viewModel.uiState.value.isLoading}

        setContent {
            TasteTipsTheme {
                TasteTipsApp(viewModel)
            }
        }
    }
}