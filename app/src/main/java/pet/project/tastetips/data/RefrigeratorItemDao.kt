package pet.project.tastetips.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RefrigeratorItemDao {
    // For refrigerator items
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRefrigeratorItem(item: RefrigeratorItem)
    @Delete
    suspend fun deleteRefrigeratorItem(item: RefrigeratorItem)
    @Query("SELECT * FROM refrigeratorItems WHERE id = :id")
    fun getRefrigeratorItem(id: Int) : Flow<RefrigeratorItem>
    @Query("SELECT * FROM refrigeratorItems")
    fun getAllRefrigeratorItems() : Flow<List<RefrigeratorItem>>

    // For favourite dishes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteDish(item: MealModel)
    @Delete
    suspend fun deleteFavouriteDish(item: MealModel)
    @Query("SELECT * FROM mealModel WHERE mealId = :id")
    fun getFavouriteDish(id: Int) : Flow<MealModel>
    @Query("SELECT * FROM mealModel")
    fun getAllFavouriteDishes() : Flow<List<MealModel>>
}
