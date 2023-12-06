package com.example.myapplication_todo.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication_todo.ui.theme.MyApplication_TodoTheme

@Composable
fun MyTodoScreen(
    navController : NavController
) {
    val users = ArrayList<Users>()

    for(i in 1..100) {
        val user = Users("user-$i", (20..80).random().toString())
        users.add(user)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(users) {
            UserView(it)
        }
    }
}

@Composable
fun UserView(user:Users) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp)
    ) {
        Column() {
            Text(text=user.name, fontSize = 20.sp, modifier=Modifier.padding(10.dp))
            Text(text=user.age, fontSize = 15.sp, modifier = Modifier.padding(10.dp))

        }
    }
}

@Preview
@Composable
fun prevtodo() {
    MyApplication_TodoTheme() {
        MyTodoScreen(navController = rememberNavController())
    }
}