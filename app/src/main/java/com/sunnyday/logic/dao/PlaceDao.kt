package com.sunnyday.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnyday.SunnyDayApplication
import com.sunnyday.logic.model.Place

object PlaceDao {
    fun savePlace(place: Place){
        sharedPreferences().edit{
            putString("place",Gson().toJson(place))
        }
    }

    fun getSavedPlace():Place{
        val placeJson= sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    fun isPlaceSaved()= sharedPreferences().contains("place")

    private fun sharedPreferences()=SunnyDayApplication.context.getSharedPreferences("sunny_day",Context.MODE_PRIVATE)

}