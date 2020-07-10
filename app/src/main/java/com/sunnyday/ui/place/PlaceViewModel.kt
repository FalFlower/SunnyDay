package com.sunnyday.ui.place

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyday.logic.Repository
import com.sunnyday.logic.model.Place

class PlaceViewModel :ViewModel(){
    private val searchLiveData=MutableLiveData<String>()

    val placeList=ArrayList<Place>()

    val placeLiveData=Transformations.switchMap(searchLiveData){query->
        Log.d("PlaceViewModel", "Repository.searchPlaces(query): ${Repository.searchPlaces(query)}")
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query:String){
        searchLiveData.value=query
    }
}