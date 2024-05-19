package pet.project.tastetips.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RefrigeratorItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: RefrigeratorItem)
    @Delete
    suspend fun delete(item: RefrigeratorItem)
    @Query("SELECT * FROM refrigeratorItems WHERE id = :id")
    fun getItem(id: Int) : Flow<RefrigeratorItem>
    @Query("SELECT * FROM refrigeratorItems")
    fun getAllItems() : Flow<List<RefrigeratorItem>>
}
