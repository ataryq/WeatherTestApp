package com.example.wethertestapp.service

import com.example.wethertestapp.data.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {
    @GET("weather?units=metric")
    fun getWeather(@Query("lat") lat: String,
                   @Query("lon") lon: String,
                   @Query("appid") apiKay: String): Call<WeatherResponse>
}