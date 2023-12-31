package com.example.study_choi

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.study_choi.taskdto.TaskResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskViewModel : ViewModel() {
    private val _todoList: MutableState<List<TaskResponseDTO>> = mutableStateOf(mutableStateListOf())
    val todoList: MutableState<List<TaskResponseDTO>>
        get() = _todoList

    private val _todoItem: MutableState<TaskResponseDTO> = mutableStateOf(TaskResponseDTO(listOf(), "", 0, ""))
    val todoItem: MutableState<TaskResponseDTO>
        get() = _todoItem

    fun getAllUsersTask(userViewModel: UserViewModel) {
        val taskAPI = RetrofitBuilder.getInstanceFor().create(TaskControl::class.java)
        val result = taskAPI.getAllTask(userViewModel.userId.value)
        result.enqueue(object : Callback<List<TaskResponseDTO>> {
            override fun onResponse(
                call: Call<List<TaskResponseDTO>>,
                response: Response<List<TaskResponseDTO>>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    _todoList.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<List<TaskResponseDTO>>, t: Throwable) {

            }
        })
    }

    fun storeTaskInfo(item: TaskResponseDTO) {
        _todoItem.value = item
    }
}