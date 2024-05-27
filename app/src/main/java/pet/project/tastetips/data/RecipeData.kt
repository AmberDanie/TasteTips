package pet.project.tastetips.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class RecipeData(
    @SerializedName(value = "results")
    val meals: List<MealModel>
)

@Entity(tableName = "mealModel")
data class MealModel(
    @PrimaryKey(autoGenerate = true)
    val mealId: Int = 0,
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
    @Embedded
    @SerializedName(value = "nutrition")
    val nutrition: NutritionData?,
    @SerializedName(value = "sections")
    val sections: List<SectionsModel>
)

@Entity(tableName = "instructions")
data class InstructionsData(
    @SerializedName(value = "display_text")
    val instructionText: String
)

@Entity(tableName = "tags")
data class TagsData(
    @SerializedName(value = "type")
    val tagType: String,
    @SerializedName(value = "display_name")
    val tagName: String
)

@Entity(tableName = "nutrition")
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

@Entity(tableName = "sections")
data class SectionsModel(
    @PrimaryKey(autoGenerate = true)
    val sectionId: Int = 0,
    @SerializedName(value = "components")
    val components: List<ComponentData>,
    @SerializedName(value = "name")
    val name: String?,
)

@Entity(tableName = "component")
data class ComponentData(
    @SerializedName(value = "raw_text")
    val componentText: String
)