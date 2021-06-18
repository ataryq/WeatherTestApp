package com.example.wethertestapp.service

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherMapApiHelper {
    companion object {
        private const val BaseUrl: String = "https://api.openweathermap.org/data/2.5/"
        private const val BaseIconUrl: String = "https://openweathermap.org/img/w/"
        const val ApiKey: String = "8c0a16f06f9d59384a7f29d47c07a0c5"

        fun loadIcon(imageView: ImageView, appContext: Context, urlIcon: String?) {
            Glide.with(appContext)
                .load("$BaseIconUrl$urlIcon.png")
                .into(imageView)
        }

        fun getApi(): OpenWeatherMapApi {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(OpenWeatherMapApi::class.java)
        }
    }
}