package pet.project.tastetips

import androidx.annotation.StringRes
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import pet.project.tastetips.data.getNavigationItems

enum class NavGraph(@StringRes val title: Int) {
    Authorization(title = R.string.authorization),
    Refrigerator(title = R.string.refrigerator),
    Recipes(title = R.string.recipes),
    RecipeInfo(title = R.string.recipe_info),
    Favorite(title = R.string.favorite),
    Settings(title = R.string.settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasteTipsBottomBar(navController: NavHostController,
                       selectedScreenIndex: Int,
                       onNavigationBarClick: (Int) -> Unit) {
    val items = getNavigationItems()
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedScreenIndex == index,
                onClick = {
                    onNavigationBarClick(index)
                    navController.navigate(item.title)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            /* TODO */
                        }
                    ) {
                        Icon(
                            imageVector = if (index==selectedScreenIndex)
                                item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }
}