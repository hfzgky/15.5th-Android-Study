package com.example.study_choi.userdto

import com.google.gson.annotations.SerializedName

data class UserRequestDTO(
    @SerializedName("loginId")
    val loginId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("userId")
    val userId: Int? = null
)
