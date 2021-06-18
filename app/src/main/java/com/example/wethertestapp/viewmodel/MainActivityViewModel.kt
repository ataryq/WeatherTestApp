package com.example.wethertestapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wethertestapp.LocationProvider
import com.example.wethertestapp.MainActivity
import com.example.wethertestapp.data.WeatherResponse
import com.example.wethertestapp.database.WeatherDatabase
import com.example.wethertestapp.database.WeatherDatabaseDao
import com.example.wethertestapp.service.OpenWeatherMapApi
import com.example.wethertestapp.service.OpenWeatherMapApiHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MainActivityViewModel: ViewModel() {

    private lateinit var mMainActivity: MainActivity
    private lateinit var mLocationProvider: LocationProvider
    private lateinit var mApi: OpenWeatherMapApi
    private lateinit var mDb: WeatherDatabaseDao

    public fun initialize(mainActivity: MainActivity) {
        mMainActivity = mainActivity
        mLocationProvider = LocationProvider(mMainActivity)
        mApi = OpenWeatherMapApiHelper.getApi()
        mDb = WeatherDatabase.createInstance(mainActivity).weatherDao()
    }

    public fun getWeather(): LiveData<List<WeatherData>> {
        return mDb.getAll()
    }

    public fun loadWeather() {
        var lastLocation = mLocationProvider.getLastLocation()
        val request = mApi.getWeather(lastLocation?.latitude.toString(),
            lastLocation?.longitude.toString(),
            OpenWeatherMapApiHelper.ApiKey)

        println("request " + request.request().url)

        request.enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if(response.isSuccessful) {
                    if(response.body() == null) {
                        println("myweather: body is null")
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        if(response == null)
                            println("response == null")

                        mDb.clearTable()
                        mDb.insertAll(WeatherData(0, response.body()!!.coord!!.lat.toString(),
                            response.body()!!.coord!!.lon.toString(),
                            response.body()!!.main!!.temp.toString(),
                            response.body()!!.wind!!.speed.toString(),
                            response.body()!!.weather!![0]!!.icon))
                    }
                }
                else {
                    println("myweather: problems " + response.message())
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                println("onFailure: ")
            }
        })
    }
}