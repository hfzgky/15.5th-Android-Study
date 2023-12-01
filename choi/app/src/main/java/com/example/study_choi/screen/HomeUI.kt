package com.example.study_choi.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.study_choi.ui.theme.Study_choiTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController : NavHostController) {
    Column {
        androidx.compose.material3.TopAppBar (
            title = { Text("Todo List") },
            // drawer 구현 예정
            navigationIcon = {
                IconButton(onClick = {  }) {
                    Icon(Icons.Filled.Menu, "Menu")
                }
            },
            actions = {
                IconButton(onClick = { navController.navigate(AllUI.AddTodoItem.name) }) {
                    Icon(Icons.Filled.Add, "todo add")
                }
            }
        )
    }
}

@Composable
fun itemCard(order: Int, navController: NavHostController) {
    Card(
        Modifier
            .padding(12.dp)
            .border(width = 4.dp, color = Color.Black)
            .fillMaxWidth()
            .height(100.dp)
            .clickable { navController.navigate(AllUI.TodoItem.name) }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("list $order")
        }
    }
}


@Composable
fun Home(navController : NavHostController) {

    Column {
        TopAppBar(navController = navController)
        // todo_list 항목들 리스트뷰로
        LazyColumn {
            itemsIndexed(
                listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            ) {
                _, item -> itemCard(order = item, navController = navController)
            }
        }
    }

}

@Composable
@Preview
fun previewHome() {
    Study_choiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Home(navController = rememberNavController())
        }
    }
}