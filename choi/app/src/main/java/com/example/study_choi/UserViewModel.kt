package com.example.study_choi

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.study_choi.userdto.UserResponseDTO
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val _userId: MutableState<Int> = mutableStateOf(0)
    val userId: State<Int>
        get() = _userId

    private val _name: MutableState<String> = mutableStateOf("")
    val name: State<String>
        get() = _name

    private val _loginId: MutableState<String> = mutableStateOf("")
    val loginId: State<String>
        get() = _loginId

    private val _password: MutableState<String> = mutableStateOf("")
    val password: State<String>
        get() = _password

    fun getUserInfo(response: Response<UserResponseDTO>) {
        _userId.value = response.body()?.userId!!
        _name.value = response.body()?.name.toString()
        _loginId.value = response.body()?.loginId.toString()
        _password.value = response.body()?.password.toString()
    }
}