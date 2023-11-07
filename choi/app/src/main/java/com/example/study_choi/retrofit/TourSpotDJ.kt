package com.example.study_choi.retrofit

import com.example.study_choi.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TourSpotDJ {
    companion object {
        private const val AUTH_KEY = BuildConfig.TOUR_API_KEY
    }

    @GET("gettourspot")
    fun getTourSpot(
        @Query("serviceKey") serviceKey : String = AUTH_KEY,
        @Query("pageNo") pageNo : String = "1",
        @Query("numOfRows") numOfRows : String = "1"
    ): Call<TourDTO>
}