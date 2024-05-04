package com.example.tastetips.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tastetips.model.TasteTipsViewModel
import com.example.tastetips.ui.theme.TasteTipsTheme

@Composable
fun SettingsScreen(navController: NavController, viewModel: TasteTipsViewModel) {

}

@Preview
@Composable
fun SettingsScreenPreview() {
    TasteTipsTheme {
        SettingsScreen(rememberNavController(), viewModel())
    }
}