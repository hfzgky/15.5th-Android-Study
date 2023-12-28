package com.example.study_choi.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.study_choi.RetrofitBuilder
import com.example.study_choi.UserControl
import com.example.study_choi.ui.theme.Study_choiTheme
import com.example.study_choi.userdto.UserRequestDTO
import com.example.study_choi.userdto.UserResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SignUp(navController: NavHostController) {
    val context = LocalContext.current
    val nameTextState = remember { mutableStateOf("") }
    val idTextState = remember { mutableStateOf("") }
    val pwTextState = remember { mutableStateOf("") }
    val pwConfirmTextState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(128.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "이름") },
            value = nameTextState.value,
            onValueChange = { nameTextState.value = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "ID") },
            value = idTextState.value,
            onValueChange = { idTextState.value = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "비밀번호") },
            value = pwTextState.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { pwTextState.value = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 비밀번호 확인
        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "비밀번호 확인") },
            value = pwConfirmTextState.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { pwConfirmTextState.value = it }
        )

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = {
                if (checkIdEmail(idTextState)) {
                    try {
                        val requestDTO = UserRequestDTO(
                            loginId = idTextState.value,
                            name = nameTextState.value,
                            password = pwTextState.value
                        )
                        val userAPI = RetrofitBuilder.getInstanceFor().create(UserControl::class.java)
                        val result = userAPI.addNewUser(requestDTO)
                        result.enqueue(object : Callback<UserResponseDTO> {
                            override fun onResponse(
                                call: Call<UserResponseDTO>,
                                response: Response<UserResponseDTO>
                            ) {
                                if (response.isSuccessful) {
                                    Toast.makeText(context, "계정 생성이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                    navController.navigate(AllUI.LogIn.name)
                                } else if (response.code() == 400) {
                                    if (pwTextState.value.length < 6)
                                        Toast.makeText(context, "비밀번호는 6자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                                    Log.e("err", response.errorBody()?.toString()!!)
                                }
                            }
                            override fun onFailure(call: Call<UserResponseDTO>, t: Throwable) {
                                Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show()
                            }
                        })

                    } catch (e: NullPointerException) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "이메일 형식으로 ID를 작성해 주세요", Toast.LENGTH_SHORT).show()
                }
            },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(16.dp, 0.dp)
        ) {
            Text(text = "Sign Up")
        }
    }
}

fun checkIdEmail(id: MutableState<String>): Boolean {
    return "@" in id.value && id.value.count { it == '@' } == 1
}

@Composable
@Preview
fun previewSign() {
    Study_choiTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SignUp(navController = rememberNavController())
        }
    }
}