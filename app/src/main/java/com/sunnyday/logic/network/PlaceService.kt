package com.sunnyday.logic.network


import com.sunnyday.SunnyDayApplication
import com.sunnyday.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${SunnyDayApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query:String): Call<PlaceResponse>
    //返回值声明为Call<PlaceService>后 retrofit会将服务器返回的JSON数据自动解析成PlaceResponse对象
}