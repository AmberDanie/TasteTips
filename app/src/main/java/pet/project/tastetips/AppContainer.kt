package pet.project.tastetips

import android.content.Context
import pet.project.tastetips.data.RefrigeratorDatabase
import pet.project.tastetips.network.FoodApiService
import pet.project.tastetips.network.TasteTipsRepository
import pet.project.tastetips.network.TasteTipsRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val tasteTipsRepository: TasteTipsRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://tasty.p.rapidapi.com/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: FoodApiService by lazy {
        retrofit.create(FoodApiService::class.java)
    }

    private val refrigeratorDao =
        RefrigeratorDatabase.getDataBase(context).itemDao()

    override val tasteTipsRepository: TasteTipsRepository by lazy {
        TasteTipsRepositoryImpl(retrofitService, refrigeratorDao)
    }
}