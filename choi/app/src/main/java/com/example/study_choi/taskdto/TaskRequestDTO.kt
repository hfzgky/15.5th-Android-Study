package com.example.study_choi.taskdto

import com.google.gson.annotations.SerializedName

data class TaskRequestDTO(
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("taskId")
    val taskId: Int = 0,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
)
