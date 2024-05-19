package pet.project.tastetips.ui.screens.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pet.project.tastetips.NavGraph
import pet.project.tastetips.R
import pet.project.tastetips.data.MealModel
import pet.project.tastetips.model.NetworkState
import pet.project.tastetips.model.TasteTipsViewModel

@Composable
fun RecipesScreen(navController: NavController, viewModel: TasteTipsViewModel) {
    val recipesState = viewModel.networkState
    Column() {
        RecipesSearchBar()
        when (recipesState) {
            is NetworkState.Success ->
                FoodColumn(mealData = recipesState.data.meals,
                    navigateToPositionInfo = {
                        viewModel.openRecipeInfo(it)
                        navController.navigate(
                            route = NavGraph.RecipeInfo.name
                        )
                    })
            is NetworkState.Error -> {}
            is NetworkState.Loading -> {}
        }
    }
}

@Composable
fun FoodColumn(
    mealData: List<MealModel>,
    navigateToPositionInfo: (MealModel) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp))
{
    LazyColumn(
        modifier = modifier.padding(top = 0.dp, start = 10.dp,
            end = 10.dp, bottom = 10.dp),
        contentPadding = contentPadding
    ) {
        items(items = mealData) { position ->
            RecipeItem(position = position, navigateToInfo = navigateToPositionInfo)
        }
    }
}

@Composable
fun RecipeItem(position: MealModel, navigateToInfo: (MealModel) -> Unit) {
    val tags = position.tags.subList(0, 3)
    val elseTags = position.tags.size - 3
    Card(
        shape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp,
            bottomStart = 0.dp,
            bottomEnd = 20.dp
        ),
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clickable { navigateToInfo(position) }
    ) {
        Column(modifier = Modifier
            .height(252.dp)
            .fillMaxWidth()) {
            Box {
                FoodImageCard(position.imageUri, position.aspectRatio)
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 160.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    tags.forEach { tag ->
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                //containerColor = mainMenuTagBackground,
                                //contentColor = Color.White
                            ),
                            modifier = Modifier.width(80.dp).height(32.dp).align(Alignment.CenterVertically),
                        ) {
                            Text(text = tag.tagName,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                //color = Color.White,
                                modifier = Modifier.fillMaxHeight()
                                    .align(Alignment.CenterHorizontally)
                                    .wrapContentHeight())
                        }
                    }
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            //contentColor = Color.White
                        ),
                        modifier = Modifier.width(32.dp).height(32.dp).align(Alignment.CenterVertically)
                    ) {
                        Text(text = "${elseTags}+", fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            //color = Color.White,
                            modifier = Modifier.fillMaxHeight()
                                .align(Alignment.CenterHorizontally)
                                .wrapContentHeight())
                    }
                }
            }
            Text(text = position.name ?: "No name",
                fontFamily = FontFamily.Serif, fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp)
            ) {
                if (position.prepTime != null && position.prepTime > 0) {
                    Icon(Icons.Outlined.Schedule, "")
                    Text(
                        text = position.prepTime.toString(),
                        fontFamily = FontFamily.Serif, fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun FoodImageCard(positionUri: String?,
                  aspectRatio: String?,
                  modifier: Modifier = Modifier) {
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
        UriImage(positionUri, aspectRatio)
    }
}

@Composable
fun UriImage(positionUri: String?, aspectRatio: String?,
             modifier: Modifier = Modifier) {
    val uri: String = if (positionUri!!.contains("s3.amazonaws.com"))
        "https://images.unsplash.com/photo-1493770348161-369560ae357d?q=80&w=2070" +
                "&auto=format&fit=crop&ixlib=rb-4.0.3" +
                "&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    else positionUri
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(uri)
            .crossfade(true)
            .build(),
        error = painterResource(R.drawable.ic_broken_image),
        contentDescription = stringResource(id = R.string.recipes),
        contentScale = ContentScale.Crop,
        modifier = if (aspectRatio != null) modifier
            .fillMaxWidth() else modifier
            .scale(2.5f)
            .fillMaxWidth()
    )
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