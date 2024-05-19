package pet.project.tastetips.ui.screens.recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocalGroceryStore
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pet.project.tastetips.data.InstructionsData
import pet.project.tastetips.data.MealModel
import pet.project.tastetips.data.NutritionData
import pet.project.tastetips.data.SectionsModel
import pet.project.tastetips.data.TagsData
import pet.project.tastetips.model.TasteTipsViewModel
import pet.project.tastetips.ui.theme.applianceColor
import pet.project.tastetips.ui.theme.defaultColor
import pet.project.tastetips.ui.theme.difficultyColor
import pet.project.tastetips.ui.theme.mealColor
import pet.project.tastetips.ui.theme.occasionColor
import pet.project.tastetips.ui.theme.transparentBackground

const val fatDailyValue = 78
const val fiberDailyValue = 28
const val sugarDailyValue = 50
const val carbohydrateDailyValue = 275
const val proteinDailyValue = 50

@Composable
fun RecipeInfoScreen(navController: NavController, viewModel: TasteTipsViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val currentPosition = uiState.chosenRecipe
    if (currentPosition != null) {
        RecipeInfo(currentPosition, returnFunction = {
            navController.popBackStack()
        })
    }
}

@Composable
fun RecipeInfo(position: MealModel, 
               returnFunction: () -> Unit) {
    var curSubpageIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val tags = position.tags
    val instructions = position.instructions
    val nutrition = position.nutrition
    val sections = position.sections
    Box {
        UriImage(positionUri = position.imageUri,
            aspectRatio = position.aspectRatio,
            modifier = Modifier
                .height(300.dp)
                .align(Alignment.TopCenter)
        )
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = transparentBackground,
                contentColor = Color.White
            ),
            modifier = Modifier.padding(4.dp)
        ) {
            IconButton(
                onClick = returnFunction
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp)
                .height(64.dp)
                .background(
                    transparentBackground
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = position.name ?: "No name",
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraLight,
                fontStyle = FontStyle.Normal,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            )
        }
        Column(modifier = Modifier.padding(top = 260.dp)) {
            Card(
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Transparent)
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    IconButton(onClick = { curSubpageIndex = 0 },
                        modifier = Modifier.weight(0.25f),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (curSubpageIndex == 0)
                                transparentBackground
                            else Color.Transparent
                        )) {
                        Icon(imageVector = if (curSubpageIndex == 0)
                            Icons.Filled.Info
                        else Icons.Outlined.Info,
                            contentDescription = null)
                    }
                    IconButton(onClick = { curSubpageIndex = 1 },
                        modifier = Modifier.weight(0.25f),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (curSubpageIndex == 1)
                                transparentBackground
                            else Color.Transparent
                        )) {
                        Icon(imageVector = if (curSubpageIndex == 1)
                            Icons.Filled.ReceiptLong
                        else Icons.Outlined.ReceiptLong,
                            contentDescription = null)
                    }
                    IconButton(onClick = { curSubpageIndex = 2 },
                        modifier = Modifier.weight(0.25f),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (curSubpageIndex == 2)
                                transparentBackground
                            else Color.Transparent
                        )) {
                        Icon(imageVector = if (curSubpageIndex == 2)
                            Icons.Filled.LocalGroceryStore
                        else Icons.Outlined.LocalGroceryStore,
                            contentDescription = null)
                    }
                    IconButton(onClick = { curSubpageIndex = 3 },
                        modifier = Modifier.weight(0.25f),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (curSubpageIndex == 3)
                                transparentBackground
                            else Color.Transparent
                        )) {
                        Icon(imageVector = if (curSubpageIndex == 3)
                            Icons.Filled.VideoLibrary
                        else Icons.Outlined.VideoLibrary,
                            contentDescription = null)
                    }
                }
                when (curSubpageIndex) {
                    0 -> InfoScreen(tags = tags, nutrition = nutrition)
                    1 -> ReceiptPathScreen(instructions = instructions)
                    2 -> ProductsScreen(sections)
                    3 -> VideoScreen()
                }
            }
        }
    }
}

@Composable
fun InfoScreen(tags: List<TagsData>, nutrition: NutritionData?) {
    Text(
        text = "Tags", fontSize = 36.sp,
        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    )
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 100.dp),
        modifier = Modifier.height(200.dp)) {
        items(tags) { tag ->
            val color = when (tag.tagType) {
                "difficulty" -> difficultyColor
                "appliance" -> applianceColor
                "meal" -> mealColor
                "occasion" -> occasionColor
                else -> defaultColor
            }
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = color,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .height(24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = tag.tagName,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
    if (nutrition != null) {
        if (nutrition.fat != null) {
            Text(
                text = "Nutrition Facts", fontSize = 36.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 4.dp)
            )
            Card(shape = RectangleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Nutrient", fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Calories")
                        Text("Total Fat")
                        Text("Sugar")
                        Text("Carbohydrates")
                        Text("Fiber")
                        Text("Protein")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Amount", fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("${nutrition.calories}")
                        Text("${nutrition.fat}")
                        Text("${nutrition.sugar}")
                        Text("${nutrition.carbohydrates}")
                        Text("${nutrition.fiber}")
                        Text("${nutrition.protein}")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(
                            "Daily Dose", fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("")
                        Text("${(100 * nutrition.fat) / fatDailyValue}%")
                        Text("${(100 * nutrition.sugar!!).div(sugarDailyValue)}%")
                        Text("${(100 * nutrition.carbohydrates!!).div(carbohydrateDailyValue)}%")
                        Text("${(100 * nutrition.fiber!!).div(fiberDailyValue)}%")
                        Text("${(100 * nutrition.protein!!).div(proteinDailyValue)}%")
                    }
                }
            }
        }
    }
}

@Composable
fun ReceiptPathScreen(instructions: List<InstructionsData>) {
    Text(
        text = "Full Recipe", fontSize = 36.sp,
        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    )
    LazyColumn(
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        itemsIndexed(instructions) { index, instruction ->
            Text("${index+1}. ${instruction.instructionText}")
        }
    }
}

@Composable
fun ProductsScreen(sections: List<SectionsModel>) {
    Text(
        text = "Components List", fontSize = 36.sp,
        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    )
    LazyColumn(
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        items(sections) { section ->
            val components = section.components
            Text(section.name ?: "Products", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            for (component in components) {
                Text(component.componentText)
            }
        }
    }
}

@Composable
fun VideoScreen() {
}