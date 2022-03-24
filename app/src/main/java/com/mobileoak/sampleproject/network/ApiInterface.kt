package com.mobileoak.sampleproject.network

import com.mobileoak.sampleproject.model.NetworkRequestObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("popular")
    fun getMovies(@Query("api_key") api: String, @Query("page") page: Int): Call<NetworkRequestObject>

    companion object {
        var BASE_URL = "https://api.themoviedb.org/3/movie/"

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

}