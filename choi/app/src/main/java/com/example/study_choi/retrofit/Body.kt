package com.example.study_choi.retrofit


import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("totalCount")
    val totalCount: Int
)