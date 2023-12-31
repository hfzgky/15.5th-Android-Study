package com.example.study_choi.screen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.study_choi.R
import com.example.study_choi.RetrofitBuilder
import com.example.study_choi.TaskControl
import com.example.study_choi.TaskViewModel
import com.example.study_choi.UserControl
import com.example.study_choi.UserViewModel
import com.example.study_choi.taskdto.TaskResponseDTO
import com.example.study_choi.ui.theme.Study_choiTheme
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    navController: NavHostController,
    userViewModel: UserViewModel,
    taskViewModel: TaskViewModel
) {
    val context = LocalContext.current
    val openAlertDialog = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val todoList = taskViewModel.todoList

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "User Image"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = userViewModel.name.value)
                }

                Spacer(modifier = Modifier.height(8.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(8.dp))

                NavigationDrawerItem(
                    label = { Text(text = "회원 정보") },
                    selected = false,
                    onClick = { navController.navigate(AllUI.UserInfo.name) }
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "회원 탈퇴",
                            color = Color.Red
                        )
                    },
                    selected = false,
                    onClick = {
                        openAlertDialog.value = true
                    }

                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxWidth(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("Todo List")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Menu, "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate(AllUI.AddTodoItem.name) }) {
                            Icon(Icons.Filled.Add, "todo add")
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
            content = {
                if (openAlertDialog.value) {
                    BaseDialog(
                        onDismissRequest = { openAlertDialog.value = false },
                        onConfirmation = {
                            try {
                                val userAPI = RetrofitBuilder.getInstanceFor().create(UserControl::class.java)
                                val result = userAPI.deleteUser(userViewModel.userId.value)
                                result.enqueue(object : Callback<Unit> {
                                    override fun onResponse(
                                        call: Call<Unit>,
                                        response: Response<Unit>
                                    ) {
                                        if (response.isSuccessful && response.code() == 200) {
                                            Toast.makeText(context, "계정이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                                            navController.navigate(AllUI.LogIn.name)
                                        }
                                    }

                                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                                        Log.e("errorItem", t.stackTrace.toString())
                                    }
                                })
                            } catch (e: NullPointerException) {
                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                            }
                            openAlertDialog.value = false
                        },
                        dialogTitle = "회원 탈퇴",
                        dialogText = "탈퇴를 진행하시겠습니까?",
                        icon = Icons.Default.Info
                    )
                }

                LazyColumn(
                    contentPadding = it,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    taskViewModel.getAllUsersTask(userViewModel)
                    itemsIndexed(todoList.value) { _, item ->
                        itemCard(
                            navController = navController,
                            item = item,
                            taskViewModel = taskViewModel,
                            userViewModel = userViewModel
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun itemCard(
    navController: NavHostController,
    item: TaskResponseDTO,
    taskViewModel: TaskViewModel,
    userViewModel: UserViewModel
) {
    val checked = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val df = DecimalFormat("00")

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                taskViewModel.storeTaskInfo(item)
                navController.navigate(AllUI.TodoItem.name)
            }
    ) {
        Spacer(
            Modifier
                .matchParentSize()
                .background(Color.LightGray)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = item.title,
                modifier = Modifier
                    .padding(8.dp, 0.dp, 0.dp, 0.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${item.deadline[0]}-${df.format(item.deadline[1])}-${df.format(item.deadline[2])}",
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )
                Spacer(modifier = Modifier.width(4.dp))
                Checkbox(
                    checked = checked.value,
                    onCheckedChange = {
                        checked.value = !checked.value
                        try {
                            if (checked.value) {
                                val taskAPI = RetrofitBuilder.getInstanceFor().create(TaskControl::class.java)
                                val result = taskAPI.deleteTask(item.taskId)
                                result.enqueue(object : Callback<Unit> {
                                    override fun onResponse(
                                        call: Call<Unit>,
                                        response: Response<Unit>
                                    ) {
                                        if (response.isSuccessful) {
                                            taskViewModel.getAllUsersTask(userViewModel)
                                            checked.value = !checked.value
                                        }
                                    }

                                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                                        Log.e("errorHome", t.stackTrace.toString())
                                    }
                                })

                            }
                        } catch (e: NullPointerException) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                        }

                    }
                )
            }
        }
    }
}


@Composable
fun BaseDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "alert")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    text = "삭제",
                    color = Color.Red
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("취소")
            }
        }
    )
}

@Composable
fun Home(
    navController: NavHostController,
    userViewModel: UserViewModel,
    taskViewModel: TaskViewModel
) {
    OnBackPressed()
    Column {
        HomeTopAppBar(
            navController = navController,
            userViewModel = userViewModel,
            taskViewModel = taskViewModel
        )
    }
}

@Composable
fun OnBackPressed() {
    val backPressedState = remember { mutableStateOf(true) }
    val context = LocalContext.current
    var backPressedTime = 0L
    BackHandler(enabled = backPressedState.value) {
        if (System.currentTimeMillis() - backPressedTime <= 1500) {
            (context as Activity).finish()
        } else {
            backPressedState.value = true
            Toast.makeText(context, "한 번 더 뒤로가기 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT)
                .show()
            backPressedTime = System.currentTimeMillis()
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
            Home(
                navController = rememberNavController(),
                userViewModel = UserViewModel(),
                taskViewModel = TaskViewModel()
            )
        }
    }
}