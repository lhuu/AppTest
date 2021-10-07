package com.example.apptest.data.DB

import com.example.apptest.models.itemDetails
import com.example.apptest.models.itemList
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiDBInterface {

    @GET("movie/{val}")
    fun getData(@Path("val") value: String, @Query("page") page: Int): Single<itemList >

    @GET("movie/{id}")
    fun getDataDetail(@Path("id") id: Int): Single<itemDetails >

    @GET("search/movie")
    fun searchMovie(@Query("query") query: String, @Query("page") page: Int): Call<itemList>
}