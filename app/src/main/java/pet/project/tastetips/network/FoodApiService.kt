package pet.project.tastetips.network

import pet.project.tastetips.data.RecipeData
import retrofit2.http.GET
import retrofit2.http.Headers

interface FoodApiService {
    @Headers("X-RapidAPI-Key: " + "f529140194msh1672c9ec1fc37ebp160ccejsn2c50c627c330",
        "X-RapidAPI-Host: " + "tasty.p.rapidapi.com")
    @GET("recipes/list?from=40&size=60")
    suspend fun getFoodList() : RecipeData
}