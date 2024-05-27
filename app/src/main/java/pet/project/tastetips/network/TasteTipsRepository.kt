package pet.project.tastetips.network

import kotlinx.coroutines.flow.Flow
import pet.project.tastetips.data.MealModel
import pet.project.tastetips.data.RecipeData
import pet.project.tastetips.data.RefrigeratorItem
import pet.project.tastetips.data.RefrigeratorItemDao

interface TasteTipsRepository {
    suspend fun getFoodList(): RecipeData
    fun getAllRefrigeratorItemsStream(): Flow<List<RefrigeratorItem>>
    fun getRefrigeratorItemStream(id: Int): Flow<RefrigeratorItem>
    suspend fun insertRefrigeratorItem(item: RefrigeratorItem)
    suspend fun deleteRefrigeratorItem(item: RefrigeratorItem)

    suspend fun insertFavouriteDish(item: MealModel)

    suspend fun deleteFavouriteDish(item: MealModel)

    fun getFavouriteDish(id: Int) : Flow<MealModel>

    fun getAllFavouriteDishes() : Flow<List<MealModel>>
}

class TasteTipsRepositoryImpl(
    private val foodApiService: FoodApiService,
    private val refrigeratorItemDao: RefrigeratorItemDao
) : TasteTipsRepository {
    override suspend fun getFoodList(): RecipeData = foodApiService.getFoodList()
    override fun getAllRefrigeratorItemsStream(): Flow<List<RefrigeratorItem>> =
        refrigeratorItemDao.getAllRefrigeratorItems()

    override fun getRefrigeratorItemStream(id: Int): Flow<RefrigeratorItem> =
        refrigeratorItemDao.getRefrigeratorItem(id)

    override suspend fun insertRefrigeratorItem(item: RefrigeratorItem) = refrigeratorItemDao.insertRefrigeratorItem(item)

    override suspend fun deleteRefrigeratorItem(item: RefrigeratorItem) = refrigeratorItemDao.deleteRefrigeratorItem(item)
    override suspend fun insertFavouriteDish(item: MealModel) = refrigeratorItemDao.insertFavouriteDish(item)
    override suspend fun deleteFavouriteDish(item: MealModel) = refrigeratorItemDao.deleteFavouriteDish(item)

    override fun getFavouriteDish(id: Int): Flow<MealModel> = refrigeratorItemDao.getFavouriteDish(id)

    override fun getAllFavouriteDishes(): Flow<List<MealModel>> = refrigeratorItemDao.getAllFavouriteDishes()
}