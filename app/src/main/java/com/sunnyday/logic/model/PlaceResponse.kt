package com.sunnyday.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status:String,val places:List<Place>)

data class Place(val name:String,val location:Location,@SerializedName("formatted_address") val address:String)
//由于JSON中的一些字段的命名可能与Kotlin的命名规范不太一致，因此这里使用了@SerializedName注解的方式，来让JSON字段和Kotlin字段之间建立映射关系

data class Location(val lng:String,val lat:String)