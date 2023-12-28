package com.example.study_choi.taskdto

import com.google.gson.annotations.SerializedName

data class TaskResponseDTO(
    @SerializedName("deadline")
    val deadline: List<Int>,
    @SerializedName("description")
    val description: String,
    @SerializedName("taskId")
    val taskId: Int,
    @SerializedName("title")
    val title: String
)
