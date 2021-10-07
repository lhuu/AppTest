package com.example.apptest.data.service

import com.example.apptest.data.DB.ApiDBInterface
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val apiKey = "2d5865c66d89cc9facaa2025ec5976fd"
const val Url = "https://api.themoviedb.org/3/"
const val imgUrl = "https://image.tmdb.org/t/p/w500"

const val firstPage = 1
const val itemsPage = 10

object ApiDBObject {
    fun getObject(): ApiDBInterface {
        val requestInterceptor = Interceptor { x ->
            val url:HttpUrl = x.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()

            val request:Request = x.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor x.proceed(request)
        }

        val okHttpClient:OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiDBInterface::class.java)
    }
}