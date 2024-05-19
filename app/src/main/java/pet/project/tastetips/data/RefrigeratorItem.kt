package pet.project.tastetips.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refrigeratorItems")
data class RefrigeratorItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val positionIcon: Int,
    val positionName: String,
    val positionExpirationDate: String
)

//val refrigeratorItems: MutableList<RefrigeratorItem> = mutableListOf()

// fun getAllItems(): Flow<List<RefrigeratorItem>>