package com.example.wethertestapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wethertestapp.viewmodel.WeatherData
import com.example.wethertestapp.viewmodel.WeatherTableName

@Dao
interface WeatherDatabaseDao {
    @Query("SELECT * FROM $WeatherTableName")
    fun getAll(): LiveData<List<WeatherData>>

    @Insert
    fun insertAll(vararg histories: WeatherData)

    @Delete
    fun delete(history: WeatherData)

    @Query("DELETE FROM $WeatherTableName")
    fun clearTable()
}

@Database(entities = [WeatherData::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDatabaseDao

    companion object {
        private val DbWeather = "DbWeather";

        public fun createInstance(context: Context): WeatherDatabase {
            return Room.databaseBuilder(context,
                WeatherDatabase::class.java,
                DbWeather).build()
        }
    }

}