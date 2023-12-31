package com.example.study_choi.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.study_choi.RetrofitBuilder
import com.example.study_choi.UserControl
import com.example.study_choi.UserViewModel
import com.example.study_choi.ui.theme.Study_choiTheme
import com.example.study_choi.userdto.UserRequestDTO
import com.example.study_choi.userdto.UserResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LogIn(
    navController : NavHostController,
    viewModel: UserViewModel
) {
    val context = LocalContext.current

    val idTextState = remember { mutableStateOf("") }
    val pwTextState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(128.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "User ID") },
            value = idTextState.value,
            onValueChange = { idTextState.value = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Password") },
            value = pwTextState.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { pwTextState.value = it }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                try {
                    val requestDTO = UserRequestDTO(
                        loginId = idTextState.value,
                        name = "",
                        password = pwTextState.value
                    )
                    val userAPI = RetrofitBuilder.getInstanceFor().create(UserControl::class.java)
                    val result = userAPI.userLogin(requestDTO)
                    result.enqueue(object : Callback<UserResponseDTO> {
                        override fun onResponse(
                            call: Call<UserResponseDTO>,
                            response: Response<UserResponseDTO>
                        ) {
                            if(response.isSuccessful && response.code() == 200) {
                                viewModel.getUserInfo(response)
                                navController.navigate(AllUI.Home.name)
                            } else if (response.code() == 401) {
                                Toast.makeText(
                                    context,
                                    "올바르지 않은 ID 혹은 비밀번호입니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }

                        override fun onFailure(call: Call<UserResponseDTO>, t: Throwable) {
                            Log.e("errorLog", t.stackTrace.toString())
                        }
                    })
                } catch (e: NullPointerException) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(16.dp, 0.dp)
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ClickableText(
                text = AnnotatedString("sign up"),
                onClick = { navController.navigate(AllUI.SignUp.name) },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.End
                ),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(0.dp, 0.dp, 16.dp, 0.dp)
            )
        }
    }
}

@Composable
@Preview
fun previewLog() {
    Study_choiTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LogIn(
                navController = rememberNavController(),
                viewModel = UserViewModel()
            )
        }
    }
}