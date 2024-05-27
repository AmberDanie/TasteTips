package pet.project.tastetips.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import pet.project.tastetips.data.InstructionsData
import pet.project.tastetips.data.SectionsModel
import pet.project.tastetips.data.TagsData

class Converters {
    @TypeConverter
    fun instructionsFromString(value: String): List<InstructionsData> {
        return Gson().fromJson(value, Array<InstructionsData>::class.java).toList()
    }
    @TypeConverter
    fun tagsFromString(value: String): List<TagsData> {
        return Gson().fromJson(value, Array<TagsData>::class.java).toList()
    }
    @TypeConverter
    fun sectionsFromString(value: String): List<SectionsModel> {
        return Gson().fromJson(value, Array<SectionsModel>::class.java).toList()
    }
    @TypeConverter
    fun fromInstructionsList(list: List<InstructionsData>): String {
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromTagsData(list: List<TagsData>): String {
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromSectionsData(list: List<SectionsModel>): String {
        return Gson().toJson(list)
    }
}