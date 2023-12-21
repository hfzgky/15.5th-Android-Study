package com.example.study_choi

import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT

interface TaskControl {
    @POST("/tasks")
    fun addNewTask()

    @PUT("/tasks")
    fun retouchTask()

    @DELETE("/tasks/{userId}")
    fun deleteTask(

    )
}