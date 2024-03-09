package com.example.tastetips.ui.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FreeBreakfast
import androidx.compose.material.icons.filled.KebabDining
import androidx.compose.material.icons.filled.LocalPizza
import androidx.compose.material.icons.filled.RoomService
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material.icons.outlined.BakeryDining
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Cookie
import androidx.compose.material.icons.outlined.DinnerDining
import androidx.compose.material.icons.outlined.Egg
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.FreeBreakfast
import androidx.compose.material.icons.outlined.KebabDining
import androidx.compose.material.icons.outlined.LocalPizza
import androidx.compose.material.icons.outlined.RoomService
import androidx.compose.material.icons.outlined.SetMeal
import androidx.compose.material.icons.outlined.SoupKitchen
import androidx.compose.ui.graphics.vector.ImageVector

data class RefrigeratorIcon(
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector,
    var isChosen: Boolean = false
)

val refrigeratorIcons = mutableListOf(
    RefrigeratorIcon(Icons.Outlined.BakeryDining, Icons.Filled.BakeryDining, true),
    RefrigeratorIcon(Icons.Outlined.Egg, Icons.Filled.Egg),
    RefrigeratorIcon(Icons.Outlined.Fastfood, Icons.Filled.Fastfood),
    RefrigeratorIcon(Icons.Outlined.Cake, Icons.Filled.Cake),
    RefrigeratorIcon(Icons.Outlined.FreeBreakfast, Icons.Filled.FreeBreakfast),
    RefrigeratorIcon(Icons.Outlined.SoupKitchen, Icons.Filled.SoupKitchen),
    RefrigeratorIcon(Icons.Outlined.LocalPizza, Icons.Filled.LocalPizza),
    RefrigeratorIcon(Icons.Outlined.SetMeal, Icons.Filled.SetMeal),
    RefrigeratorIcon(Icons.Outlined.KebabDining, Icons.Filled.KebabDining),
    RefrigeratorIcon(Icons.Outlined.DinnerDining, Icons.Filled.DinnerDining),
    RefrigeratorIcon(Icons.Outlined.Cookie, Icons.Filled.Cookie),
    RefrigeratorIcon(Icons.Outlined.RoomService, Icons.Filled.RoomService)
)