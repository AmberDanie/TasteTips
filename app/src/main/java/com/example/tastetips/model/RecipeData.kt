package com.example.tastetips.model

import com.google.gson.annotations.SerializedName

data class RecipeData(
    @SerializedName(value = "results")
    val meals: List<MealModel>
)

data class MealModel(
    @SerializedName(value = "thumbnail_url")
    val imageUri: String?,
    @SerializedName(value = "description")
    val description: String?,
    @SerializedName(value = "name")
    val name: String?,
    @SerializedName(value = "prep_time_minutes")
    val prepTime: Int?,
    @SerializedName(value = "aspect_ratio")
    val aspectRatio: String?
)