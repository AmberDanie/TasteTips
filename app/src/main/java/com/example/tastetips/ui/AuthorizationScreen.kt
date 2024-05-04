package com.example.tastetips.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tastetips.R
import com.example.tastetips.TasteTipsScreen
import com.example.tastetips.model.TasteTipsViewModel
import com.example.tastetips.ui.theme.TasteTipsTheme

private typealias Action = () -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationScreen(navController: NavController,
    viewModel: TasteTipsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(modifier = Modifier
        .background(color = colorResource(id = R.color.gray_bg))) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.authorization_background),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 160.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.track_inventory),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = stringResource(id = R.string.track_descr),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.height(160.dp))
                Button(
                    onClick = {
                        viewModel.updateLoginSheet()
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 80.dp, end = 80.dp),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.contrast_button)
                    )
                ) {
                    Text(stringResource(R.string.login))
                }
                Button(
                    onClick = { viewModel.updateRegisterSheet() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 80.dp, end = 80.dp),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.contrast_button)
                    )
                ) {
                    Text(stringResource(R.string.register))
                }
            }
        }

        val sheetState = rememberModalBottomSheetState()

        if (uiState.loginShellIsOpen) {
            AuthorizationModelBottomSheet(
                textForTitle = R.string.login,
                sheetState = sheetState,
                onDismissClicked = { viewModel.updateLoginSheet() },
                onSubmitClicked = {
                    navController.navigate(
                        route = TasteTipsScreen.Refrigerator.name
                    ) {
                        popUpTo(TasteTipsScreen.Authorization.name) {
                            inclusive = true
                        }
                    }
                    viewModel.updateLoginSheet()
                }
            )
        }

        if (uiState.registerShellIsOpen) {
            AuthorizationModelBottomSheet(
                textForTitle = R.string.register,
                sheetState = sheetState,
                onDismissClicked = { viewModel.updateRegisterSheet() },
                onSubmitClicked = {
                    navController.navigate(
                        route = TasteTipsScreen.Refrigerator.name
                    ) {
                        popUpTo(
                            TasteTipsScreen.Authorization.name
                        ) {
                            inclusive = true
                        }
                    }
                    viewModel.updateRegisterSheet()
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AuthorizationModelBottomSheet(
    @StringRes textForTitle: Int,
    sheetState: SheetState,
    onDismissClicked: Action,
    onSubmitClicked: Action
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissClicked
    ) {
        Column(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp)
        ) {
            Text(
                text = stringResource(textForTitle),
                fontSize = 32.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = onSubmitClicked,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.teal_700)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(text = stringResource(R.string.submit), fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    TasteTipsTheme {
        AuthorizationScreen(rememberNavController(), viewModel())
    }
}