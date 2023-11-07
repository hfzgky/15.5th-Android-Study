package com.example.study_choi.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TourSpotDJ {
    companion object {
        private const val AUTH_KEY = "LGRG0cSCo30ReUvNQDWhJbelChuEODhVAWcy0a7IeMWmsGhXzLTzC1qTlxlM5cm78fIu4Y8BYwl6wOF4TURejA=="
    }

    @GET("gettourspot")
    fun getTourSpot(
        @Query("serviceKey") serviceKey : String = AUTH_KEY,
        @Query("pageNo") pageNo : String = "1",
        @Query("numOfRows") numOfRows : String = "1"
    ): Call<TourDTO>
}