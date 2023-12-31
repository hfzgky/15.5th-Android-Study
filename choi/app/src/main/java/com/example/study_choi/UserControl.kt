package com.example.study_choi

import com.example.study_choi.userdto.UserRequestDTO
import com.example.study_choi.userdto.UserResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserControl {
    @POST("/users")
    fun addNewUser(
        @Body userRequestDTO: UserRequestDTO
    ): Call<UserResponseDTO>

    @PUT("/users")
    fun retouchUser(
        @Body userRequestDTO: UserRequestDTO
    ): Call<UserResponseDTO>

    @GET("/users/{userId}")
    fun showUserInfo(
        @Path(value = "userId") userId: Int
    ): Call<UserRequestDTO>

    @DELETE("/users/{userId}")
    fun deleteUser(
        @Path(value = "userId") userId: Int?
    ): Call<Unit>

    @POST("/users/login")
    fun userLogin(
        @Body userRequestDTO: UserRequestDTO
    ): Call<UserResponseDTO>
}