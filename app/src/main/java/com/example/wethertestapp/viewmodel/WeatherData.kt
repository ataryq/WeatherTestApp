package com.example.wethertestapp.viewmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val WeatherTableName: String = "WeatherTableName"

@Entity(tableName = WeatherTableName/*, primaryKeys = ["lat","lon"]*/)
data class WeatherData(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "lat") var lat: String,
    @ColumnInfo(name = "lon") var lon: String,
    @ColumnInfo(name = "temp") var temp: String?,
    @ColumnInfo(name = "wind") var wind: String?,
    @ColumnInfo(name = "iconUrl") var iconUrl: String?)