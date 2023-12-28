package com.example.study_choi.userdto

import com.google.gson.annotations.SerializedName

data class UserResponseDTO(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("loginId")
    val loginId: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("name")
    val name: String
)
