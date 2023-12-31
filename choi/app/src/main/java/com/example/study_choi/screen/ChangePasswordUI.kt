package com.example.study_choi.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordTopAppBar(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxWidth(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "비밀번호 변경")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            ChangePasswordContent(
                padding = it,
                userViewModel = userViewModel,
                navController = navController
            )
        }
    )
}

@Composable
fun ChangePasswordContent(
    padding: PaddingValues,
    userViewModel: UserViewModel,
    navController: NavHostController
) {
    val nowPwTextState = remember { mutableStateOf("") }
    val changePwTextState = remember { mutableStateOf("") }
    val changePwConfirmTextState = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = { Text(text = "현재 비밀번호") },
            value = nowPwTextState.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { nowPwTextState.value = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = { Text(text = "변경할 비밀번호") },
            value = changePwTextState.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { changePwTextState.value = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = { Text(text = "비밀번호 확인") },
            value = changePwConfirmTextState.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { changePwConfirmTextState.value = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val isAllCheck = passwordConfirm(
                    nPw = nowPwTextState,
                    cPw = changePwTextState,
                    cPwC = changePwConfirmTextState,
                    userViewModel = userViewModel,
                    context = context
                )
                if (isAllCheck) {
                    try {
                        val requestDTO = UserRequestDTO(
                            loginId = userViewModel.loginId.value,
                            name = userViewModel.name.value,
                            password = changePwTextState.value,
                            userId = userViewModel.userId.value
                        )
                        val userAPI = RetrofitBuilder.getInstanceFor().create(UserControl::class.java)
                        val result = userAPI.retouchUser(requestDTO)
                        result.enqueue(object : Callback<UserResponseDTO> {
                            override fun onResponse(
                                call: Call<UserResponseDTO>,
                                response: Response<UserResponseDTO>
                            ) {
                               if (response.isSuccessful) {
                                   userViewModel.getUserInfo(response = response)
                                   Toast.makeText(context, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT)
                                       .show()
                                   navController.popBackStack()
                               }
                            }

                            override fun onFailure(call: Call<UserResponseDTO>, t: Throwable) {
                                Log.e("errorCP", t.stackTrace.toString())
                            }
                        })

                    } catch (e: NullPointerException) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        ) {
            Text(text = "변경")
        }
    }
}

@Composable
fun ChangePassword(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    ChangePasswordTopAppBar(
        navController = navController,
        userViewModel = userViewModel
    )
}

fun passwordConfirm(
    nPw: MutableState<String>,
    cPw: MutableState<String>,
    cPwC: MutableState<String>,
    userViewModel: UserViewModel,
    context: Context
): Boolean {
    if (userViewModel.password.value != nPw.value) {
        Toast.makeText(context, "현재 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT)
            .show()
        return false
    }
    if (nPw.value == cPw.value) {
        Toast.makeText(context, "현재 비밀번호와 같습니다.", Toast.LENGTH_SHORT)
            .show()
        return false
    }

    if (cPw.value != cPwC.value) {
        Toast.makeText(context, "변경할 비밀번호가 다릅니다", Toast.LENGTH_SHORT)
            .show()
        return false
    }

    return true
}

@Preview
@Composable
fun prevChangePassword() {
    Study_choiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ChangePassword(
                navController = rememberNavController(),
                userViewModel = UserViewModel()
            )
        }
    }
}