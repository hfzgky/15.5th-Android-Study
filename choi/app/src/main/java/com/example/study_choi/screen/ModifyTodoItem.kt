package com.example.study_choi.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.study_choi.RetrofitBuilder
import com.example.study_choi.TaskControl
import com.example.study_choi.TaskViewModel
import com.example.study_choi.UserViewModel
import com.example.study_choi.taskdto.TaskRequestDTO
import com.example.study_choi.taskdto.TaskResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyTodoItemTopAppBar(
    navController: NavHostController,
    userViewModel: UserViewModel,
    taskViewModel: TaskViewModel
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Todo List",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Menu")
                    }
                }
            )
        },
        content = {
            ModifyTodoItemContent(
                padding = it,
                userViewModel = userViewModel,
                taskViewModel = taskViewModel,
                navController = navController
            )
        }
    )
}

@Composable
fun ModifyTodoItemContent(
    padding: PaddingValues,
    userViewModel: UserViewModel,
    taskViewModel: TaskViewModel,
    navController: NavHostController
) {
    val df = DecimalFormat("00")
    val item = taskViewModel.todoItem.value
    val context = LocalContext.current

    val openTimeDialog = remember { mutableStateOf(false) }
    val openDateDialog = remember { mutableStateOf(false) }

    val deadLineDate = remember { mutableStateOf("${item.deadline[0]}-${df.format(item.deadline[1])}-${df.format(item.deadline[2])}") }
    val deadLineTime = remember { mutableStateOf("${df.format(item.deadline[3])}:${df.format(item.deadline[4])}") }

    val titleTextState = remember { mutableStateOf(item.title) }
    val descriptionTextState = remember { mutableStateOf(item.description) }

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(20.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Title") },
            value = titleTextState.value,
            onValueChange = { titleTextState.value = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Description") },
            value = descriptionTextState.value,
            onValueChange = { descriptionTextState.value = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            Button(onClick = {
                openDateDialog.value = true
            }) {
                Text(text = deadLineDate.value)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                openTimeDialog.value = true
            }) {
                Text(text = deadLineTime.value)
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                try {
                    val requestDTO = TaskRequestDTO(
                        deadline = deadLineDate.value + "T" + deadLineTime.value,
                        description = descriptionTextState.value,
                        taskId = item.taskId,
                        title = titleTextState.value,
                        userId = userViewModel.userId.value
                    )
                    val todoAPI = RetrofitBuilder.getInstanceFor().create(TaskControl::class.java)
                    val result = todoAPI.retouchTask(requestDTO)
                    result.enqueue(object : Callback<TaskResponseDTO> {
                        override fun onResponse(
                            call: Call<TaskResponseDTO>,
                            response: Response<TaskResponseDTO>
                        ) {
                            if (response.isSuccessful) {
                                navController.popBackStack()
                                taskViewModel.storeTaskInfo(
                                    TaskResponseDTO(
                                        deadline = response.body()?.deadline ?: item.deadline,
                                        description = response.body()?.description ?: item.description,
                                        taskId = response.body()?.taskId ?: item.taskId,
                                        title = response.body()?.title ?: item.title
                                    )
                                )
                                Toast.makeText(context, "일정이 수정 되었습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<TaskResponseDTO>, t: Throwable) {
                            Log.e("errorModify", t.stackTrace.toString())
                        }
                    })
                }catch (e: NullPointerException) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(16.dp, 0.dp)
        ) {
            Text(text = "수정")
        }

        if (openDateDialog.value) {
            DeadlineDatePickerDialog(
                openDateDialog = openDateDialog,
                deadLineDate = deadLineDate
            )
        }

        if (openTimeDialog.value) {
            DeadLineTimePickerDialog(
                openTimeDialog = openTimeDialog,
                deadLineTime = deadLineTime
            )
        }
    }
}

@Composable
fun ModifyTodoItem(
    navController: NavHostController,
    userViewModel: UserViewModel,
    taskViewModel: TaskViewModel
) {
    ModifyTodoItemTopAppBar(
        navController = navController,
        userViewModel = userViewModel,
        taskViewModel = taskViewModel
    )

}