package com.example.myapplication_todo.Screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun TodoScreenList() {
    val navController = rememberNavController()

    //navigate
    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen.name
    ){
        composable(Screens.LoginScreen.name){
            LoginScreen(
                navController = navController
            )
        }
        composable(Screens.SignUPScreen.name) {
            SignUpScreen(
                navController = navController
            )
        }
        composable(Screens.MyTodoScreen.name) {
            MyTodoScreen(
                navController = navController
            )
        }
        /*
        composable(Screens.HomeScreen.name){
            HomeScreen(
                navController = navController
            )
        }
        composable(Screens.AddUserScreen.name){
            AddUserScreen(
                navController = navController
            )
        }

         */
    }

}