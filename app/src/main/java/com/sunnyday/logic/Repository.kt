package com.sunnyday.logic

import androidx.lifecycle.liveData
import com.sunnyday.logic.dao.PlaceDao
import com.sunnyday.logic.model.Place
import com.sunnyday.logic.model.Weather
import com.sunnyday.logic.network.SunnyDayNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext


object Repository {
    fun searchPlaces(query:String) = fire(Dispatchers.IO) {
        val placeResponse=SunnyDayNetwork.searchPlaces(query)
        if (placeResponse.status=="ok"){
            val places=placeResponse.places
            Result.success(places)
        } else{
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng:String,lat:String)= fire(Dispatchers.IO){
        coroutineScope {
            val deferredRealtime=async {
                SunnyDayNetwork.getRealtimeWeather(lng,lat)
            }
            val deferredDaily=async {
                SunnyDayNetwork.getDailyWeather(lng,lat)
            }
            val realtimeResponse=deferredRealtime.await()
            val dailyResponse=deferredDaily.await()
            if (realtimeResponse.status=="ok"&&dailyResponse.status=="ok"){
                val weather= dailyResponse.result.daily?.let {
                        Weather(realtimeResponse.result.realtime, it)
                    }
                Result.success(weather)
            }else{
                Result.failure(
                    RuntimeException(
                        "realtime response status is${realtimeResponse.status}" + "daily response status is${dailyResponse.status}"
                    )
                )
            }
        }
    }

    //进行异常处理的统一接口 suspend用于表明所有传入Lambda表达式中的代码也是拥有挂起函数上下文的
    private fun <T> fire(context:CoroutineContext,block:suspend ()->Result<T>)= liveData<Result<T>>(context){
        val result=try {
            block()
        }catch (e:Exception){
            Result.failure<T>(e)
        }
        emit(result)
    }

    //应该在子线程中操作
    fun savePlace(place: Place)=PlaceDao.savePlace(place)

    fun getSavedPlace()=PlaceDao.getSavedPlace()

    fun isPlaceSaved()=PlaceDao.isPlaceSaved()
}