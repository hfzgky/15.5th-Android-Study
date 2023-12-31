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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.study_choi.RetrofitBuilder
import com.example.study_choi.TaskControl
import com.example.study_choi.TaskViewModel
import com.example.study_choi.ui.theme.Study_choiTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

@Composable
fun DropDownMenuSample(
    navController: NavHostController,
    context: Context,
    dropdownMenuExpanded: Boolean,
    taskViewModel: TaskViewModel,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        expanded = dropdownMenuExpanded,
        onDismissRequest = onDismiss,
        offset = DpOffset(x = 0.dp, y = 0.dp)
    ) {
        DropdownMenuItem(
            onClick = {
                navController.navigate(AllUI.ModifyTodoItem.name)
                onDismiss()
            },
            text = {
                Text(text = "수정")
            }
        )
        DropdownMenuItem(
            onClick = {
                try {
                    val taskAPI = RetrofitBuilder.getInstanceFor().create(TaskControl::class.java)
                    val result = taskAPI.deleteTask(taskViewModel.todoItem.value.taskId)
                    result.enqueue(object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "할 일이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                                navController.navigate(AllUI.Home.name)
                            }
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            Log.e("errorItem", t.stackTrace.toString())
                        }
                    })
                    onDismiss()
                } catch (e: NullPointerException) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }

            },
            text = {
                Text(text = "삭제")
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItemTopAppBar(
    navController: NavHostController,
    taskViewModel: TaskViewModel
) {
    val context = LocalContext.current
    var dropdownMenuExpanded by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Todo List",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        dropdownMenuExpanded = true
                    }) {
                        Icon(Icons.Filled.MoreVert, "todoModifyDelete")
                    }

                    DropDownMenuSample(
                        navController,
                        context,
                        dropdownMenuExpanded,
                        taskViewModel
                    ) {
                        dropdownMenuExpanded = false
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        content = {
            TodoItemContent(
                padding = it,
                taskViewModel = taskViewModel
            )
        }
    )
}

@Composable
fun TodoItemContent(
    padding: PaddingValues,
    taskViewModel: TaskViewModel
) {
    val df = DecimalFormat("00")
    val item = taskViewModel.todoItem.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = item.title, fontSize = 32.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 8.dp, 0.dp),
            text = "${item.deadline[0]}-${df.format(item.deadline[1])}-${df.format(item.deadline[2])} ${df.format(item.deadline[3])}:${df.format(item.deadline[4])}",
            fontSize = 16.sp,
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 0.dp, 0.dp),
            text = item.description,
            fontSize = 20.sp
        )
    }
}

@Composable
fun TodoItem(
    navController: NavHostController,
    taskViewModel: TaskViewModel
) {
    TodoItemTopAppBar(
        navController = navController,
        taskViewModel = taskViewModel
    )
}

@Preview
@Composable
fun previewTodoItem() {
    Study_choiTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

        }
    }
}