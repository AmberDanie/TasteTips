package com.example.tastetips.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tastetips.R
import com.example.tastetips.model.MealModel
import com.example.tastetips.model.RecipesUiState
import com.example.tastetips.model.TasteTipsViewModel
import com.example.tastetips.ui.theme.TasteTipsTheme

@Composable
fun RecipesScreen(viewModel: TasteTipsViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val recipesState = viewModel.recipesUiState
    Column() {
        RecipesSearchBar()
        when (recipesState) {
            is RecipesUiState.Success -> FoodColumn(recipesState.data.meals)
            is RecipesUiState.Error -> {}
            is RecipesUiState.Loading -> {}
        }
    }
}

@Composable
fun FoodColumn(
    mealData: List<MealModel>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp))
{
    LazyColumn(
        modifier = modifier.padding(top = 0.dp, start = 10.dp,
            end = 10.dp, bottom = 10.dp),
        contentPadding = contentPadding
    ) {
        items(items = mealData) { position ->
            val posName = position.name ?: "No name"
            val posPrepTime = if (position.prepTime != null && position.prepTime > 0)
                position.prepTime.toString() else "???"
            Card(
                backgroundColor = colorResource(id = R.color.gray_container),
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 20.dp
                ),
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    FoodCard(position.imageUri, position.aspectRatio)
                    Text(text = posName,
                        fontFamily = FontFamily.Serif, fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp))
                    Row(modifier = Modifier.align(Alignment.End).padding(end = 8.dp)) {
                        Icon(Icons.Outlined.Schedule, "")
                        Text(text = posPrepTime,
                            fontFamily = FontFamily.Serif, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun FoodCard(positionUri: String?,
             aspectRatio: String?,
             modifier: Modifier = Modifier) {
    val uri: String = if (positionUri!!.contains("s3.amazonaws.com"))
        "https://images.unsplash.com/photo-1493770348161-369560ae357d?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    else positionUri
    Card(
        modifier = modifier
            .height(200.dp),
        shape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(uri)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            contentDescription = stringResource(id = R.string.recipes),
            contentScale = ContentScale.Crop,
            modifier = if (aspectRatio != null) Modifier
                .fillMaxWidth() else Modifier.scale(2.5f).fillMaxWidth()
        )
    }
}

@Composable
fun RecipesSearchBar(modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = {

        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = colorResource(id = R.color.teal_700),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RecipesScreenPreview() {
    TasteTipsTheme {
        RecipesScreen(viewModel())
    }
}