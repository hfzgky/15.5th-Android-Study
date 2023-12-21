package com.example.study_choi

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.study_choi.screen.AddTodoItem
import com.example.study_choi.screen.AllUI
import com.example.study_choi.screen.ChangePassword
import com.example.study_choi.screen.Home
import com.example.study_choi.screen.LogIn
import com.example.study_choi.screen.SignUp
import com.example.study_choi.screen.TodoItem
import com.example.study_choi.screen.UserInfo

@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AllUI.LogIn.name
    ) {
        composable(AllUI.LogIn.name) {
             LogIn(
                navController = navController
             )
        }

        composable(AllUI.Home.name) {
            Home(
                navController = navController
            )
        }

        composable(AllUI.SignUp.name) {
            SignUp(
                navController = navController
            )
        }

        composable(AllUI.TodoItem.name) {
            TodoItem(
                navController = navController
            )
        }

        composable(AllUI.AddTodoItem.name) {
            AddTodoItem(
                navController = navController
            )
        }

        composable(AllUI.UserInfo.name) {
            UserInfo(
                navController = navController
            )
        }

        composable(AllUI.ChangePassword.name) {
            ChangePassword(
                navController = navController
            )
        }
    }
}