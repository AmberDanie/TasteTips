package com.example.tastetips.data

import com.example.tastetips.model.RecipeData
import com.example.tastetips.network.FoodApiService

interface TasteTipsRepository {
    suspend fun getFoodList() : RecipeData
}

class NetworkTasteTipsRepository (
    private val foodApiService: FoodApiService
) : TasteTipsRepository {
    override suspend fun getFoodList(): RecipeData = foodApiService.getFoodList()
}