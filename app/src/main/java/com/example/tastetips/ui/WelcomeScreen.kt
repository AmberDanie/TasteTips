package com.example.tastetips.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tastetips.R
import com.example.tastetips.ui.theme.TasteTipsTheme

@Composable
fun WelcomeScreen(
    onStartButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(id = R.string.welcome_screen_description),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(bottom = 48.dp)
        )
        Image(painter = painterResource(id = R.drawable.refrigerator),
            contentDescription = null,
            modifier = Modifier.align(CenterHorizontally))
        Spacer(
           modifier = Modifier.padding(20.dp)
        )
        Button(
            onClick = onStartButtonClicked,
            shape = RoundedCornerShape(5),
            modifier = Modifier.fillMaxWidth(fraction = 0.8f),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.teal_700)
            )
        ) {
            Text(
                text = stringResource(R.string.start),
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    TasteTipsTheme {
        WelcomeScreen(onStartButtonClicked = {})
    }
}