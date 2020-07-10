package com.sunnyday.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.sunnyday.logic.model.Place
import com.sunnyday.logic.network.SunnyDayNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException


object Repository {
    fun searchPlaces(query:String) = liveData(Dispatchers.IO) {
        val placeResponse=SunnyDayNetwork.searchPlaces(query)
        val result=try {
            if (placeResponse.status=="ok"){
                val places=placeResponse.places
                Result.success(places)
            } else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}