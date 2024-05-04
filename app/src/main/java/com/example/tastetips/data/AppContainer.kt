package com.example.tastetips.data

import com.example.tastetips.network.FoodApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val tasteTipsRepository: TasteTipsRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://tasty.p.rapidapi.com/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: FoodApiService by lazy {
        retrofit.create(FoodApiService::class.java)
    }

    override val tasteTipsRepository: TasteTipsRepository by lazy {
        NetworkTasteTipsRepository(retrofitService)
    }
}