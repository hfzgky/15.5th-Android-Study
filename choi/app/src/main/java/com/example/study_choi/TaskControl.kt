package com.example.study_choi

import com.example.study_choi.taskdto.TaskRequestDTO
import com.example.study_choi.taskdto.TaskResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskControl {
    @POST("tasks")
    fun addNewTask(
        @Body taskRequestDto: TaskRequestDTO
    ): Call<TaskResponseDTO>

    @PUT("tasks")
    fun retouchTask(
        @Body taskRequestDto: TaskRequestDTO
    ): Call<TaskResponseDTO>

    @DELETE("tasks/{taskId}")
    fun deleteTask(
        @Path(value = "taskId") taskId: Int
    ): Call<Unit>

    @GET("tasks/{userId}")
    fun getAllTask(
        @Path(value = "userId") userId: Int
    ): Call<List<TaskResponseDTO>>

    @PATCH("tasks/completed/{taskId}")
    fun completedTodo(
        @Path(value = "taskId") taskId: Int
    ): Call<Unit>
}