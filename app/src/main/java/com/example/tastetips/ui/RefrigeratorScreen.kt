package com.example.tastetips.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tastetips.R
import com.example.tastetips.ui.data.RefrigeratorItem
import com.example.tastetips.ui.model.TasteTipsViewModel
import com.example.tastetips.ui.theme.TasteTipsTheme

@SuppressLint("MutableCollectionMutableState",
    "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RefrigeratorScreen(viewModel: TasteTipsViewModel) {
    val currentProducts = remember { mutableStateListOf<RefrigeratorItem>() }

    viewModel.getRefrigeratorItems(currentProducts)

    Card(
        shape = RoundedCornerShape(
            topStart = 20.dp, topEnd = 20.dp),
            modifier = Modifier.padding(40.dp)
    ) {
        Scaffold(
            topBar = { RefrigeratorTopBar() },
            bottomBar = { RefrigeratorBottomBar{
                viewModel.updateRefrigeratorItems(currentProducts)
            }},
            modifier = Modifier
                .defaultMinSize(minHeight = 800.dp)
                .fillMaxWidth(),
            containerColor = colorResource(R.color.teal_500)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .padding(top = 60.dp)
            ) {
                items(currentProducts) {
                    RefrigeratorPositionCard(it)
                }
            }
        }
    }
}

@Composable
fun RefrigeratorTopBar() {
    Surface(
        color = colorResource(id = R.color.teal_700),
        modifier = Modifier.semantics { heading() }
    ) {
        Text(
            text = "My refrigerator",
            style = MaterialTheme.typography.labelLarge,
            fontFamily = FontFamily.Cursive,
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun RefrigeratorBottomBar(onUpdateClick: () -> Unit) {
    Surface(
        color = colorResource(id = R.color.teal_700),
        modifier = Modifier.semantics { heading() }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    onUpdateClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = colorResource(R.color.teal_700)
                ),
                modifier = Modifier.align(Center)
            ) {
                Text(text = "+")
            }
        }
    }
}

@Composable
fun RefrigeratorPositionCard(refrigeratorItem: RefrigeratorItem) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(text = "List items: ${refrigeratorItem.positionName}",
            fontSize = 20.sp)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RefrigeratorScreenPreview() {
    TasteTipsTheme {
        RefrigeratorScreen(viewModel())
    }
}