package com.example.study_choi.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilderTourSpot {
    private const val BASE_URL: String = "https://apis.data.go.kr/6300000/openapi2022/tourspot/"
    var instance: Retrofit? = null

    fun getInstanceFor() : Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return instance!!
    }
}