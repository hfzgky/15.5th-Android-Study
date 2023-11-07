package com.example.study_choi.retrofit


import com.google.gson.annotations.SerializedName

data class TourDTO(
    @SerializedName("response")
    val response: Response
)