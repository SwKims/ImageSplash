package com.ksw.imagesplash.retrofit

import com.google.gson.JsonElement
import com.ksw.imagesplash.util.API
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by KSW on 2021-08-13
 */

interface RetrofitInter {

    @GET(API.SEARCH_PHOTOS)
    fun searchPhotos(@Query("query") searchTerm: String): Call<JsonElement>

    @GET(API.SEARCH_USERS)
    fun searchUsers(@Query("query") searchTerm: String): Call<JsonElement>

}