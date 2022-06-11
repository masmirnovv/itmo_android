package com.example.hw_weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/forecast?")
    fun getCurrentWeatherData(@Query("q") q: String, @Query("appid") app_id: String): Call<WeatherResponse>

}