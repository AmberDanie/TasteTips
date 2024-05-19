package pet.project.tastetips.data

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
    val aspectRatio: String?,
    @SerializedName(value = "instructions")
    val instructions: List<InstructionsData>,
    @SerializedName(value = "tags")
    val tags: List<TagsData>,
    @SerializedName(value = "nutrition")
    val nutrition: NutritionData?,
    @SerializedName(value = "sections")
    val sections: List<SectionsModel>
)

data class InstructionsData(
    @SerializedName(value = "display_text")
    val instructionText: String
)

data class TagsData(
    @SerializedName(value = "type")
    val tagType: String,
    @SerializedName(value = "display_name")
    val tagName: String
)

data class NutritionData(
    @SerializedName(value = "sugar")
    val sugar: Int?,
    @SerializedName(value = "carbohydrates")
    val carbohydrates: Int?,
    @SerializedName(value = "fiber")
    val fiber: Int?,
    @SerializedName(value = "protein")
    val protein: Int?,
    @SerializedName(value = "fat")
    val fat: Int?,
    @SerializedName(value = "calories")
    val calories: Int?
)

data class SectionsModel(
    @SerializedName(value = "components")
    val components: List<ComponentData>,
    @SerializedName(value = "name")
    val name: String?,
)

data class ComponentData(
    @SerializedName(value = "raw_text")
    val componentText: String
)