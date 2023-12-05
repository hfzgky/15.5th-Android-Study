package com.example.study_choi.screen

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.study_choi.ui.theme.Study_choiTheme

@Composable
fun SignUp(navController : NavHostController) {

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

        Spacer(modifier = Modifier.height(20.dp))

        // 비밀번호 확인
        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Password Confirm") },
            value = pwConfirmTextState.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { pwConfirmTextState.value = it }
        )

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = { navController.navigate(AllUI.LogIn.name) },
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