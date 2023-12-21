package com.example.study_choi.screen

import android.app.Activity
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
import com.example.study_choi.ui.theme.Study_choiTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(navController: NavHostController) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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

                    Text(text = "User Id")
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
                        onConfirmation = { openAlertDialog.value = false },
                        dialogTitle = "회원 탈퇴",
                        dialogText = "탈퇴를 진행하시겠습니까?",
                        icon = Icons.Default.Info
                    )
                }

                LazyColumn(
                    contentPadding = it,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(
                        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                    ) { _, _ ->
                        itemCard(navController = navController)
                    }
                }
            }
        )
    }
}

@Composable
fun itemCard(navController: NavHostController) {
    val checked = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { navController.navigate(AllUI.TodoItem.name) }
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
                text = "Todo Title",
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
                    text = "deadline",
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )
                Spacer(modifier = Modifier.width(4.dp))
                Checkbox(
                    checked = checked.value,
                    onCheckedChange = {
                        checked.value = !checked.value
                        if (checked.value) {

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
fun Home(navController: NavHostController) {
    OnBackPressed()
    Column {
        HomeTopAppBar(navController = navController)
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
            Home(navController = rememberNavController())
        }
    }
}