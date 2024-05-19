package pet.project.tastetips.network

import kotlinx.coroutines.flow.Flow
import pet.project.tastetips.data.RecipeData
import pet.project.tastetips.data.RefrigeratorItem
import pet.project.tastetips.data.RefrigeratorItemDao

interface TasteTipsRepository {
    suspend fun getFoodList(): RecipeData
    fun getAllRefrigeratorItemsStream(): Flow<List<RefrigeratorItem>>
    fun getRefrigeratorItemStream(id: Int): Flow<RefrigeratorItem>
    suspend fun insert(item: RefrigeratorItem)
    suspend fun delete(item: RefrigeratorItem)

}

class TasteTipsRepositoryImpl(
    private val foodApiService: FoodApiService,
    private val refrigeratorItemDao: RefrigeratorItemDao
) : TasteTipsRepository {
    override suspend fun getFoodList(): RecipeData = foodApiService.getFoodList()
    override fun getAllRefrigeratorItemsStream(): Flow<List<RefrigeratorItem>> =
        refrigeratorItemDao.getAllItems()

    override fun getRefrigeratorItemStream(id: Int): Flow<RefrigeratorItem> =
        refrigeratorItemDao.getItem(id)

    override suspend fun insert(item: RefrigeratorItem) = refrigeratorItemDao.insert(item)

    override suspend fun delete(item: RefrigeratorItem) = refrigeratorItemDao.delete(item)
}