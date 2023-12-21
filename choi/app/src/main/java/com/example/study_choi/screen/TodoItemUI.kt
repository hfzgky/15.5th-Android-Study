package com.example.study_choi.screen

import android.content.Context
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
import androidx.navigation.compose.rememberNavController
import com.example.study_choi.ui.theme.Study_choiTheme

@Composable
fun DropDownMenuSample(
    context: Context,
    dropdownMenuExpanded: Boolean,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        expanded = dropdownMenuExpanded,
        onDismissRequest = onDismiss,
        offset = DpOffset(x = 0.dp, y = 0.dp)
    ) {
        DropdownMenuItem(
            onClick = {
                onDismiss()
            },
            text = {
                Text(text = "수정")
            }
        )
        DropdownMenuItem(
            onClick = {
                onDismiss()
            },
            text = {
                Text(text = "삭제")
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItemTopAppBar(navController: NavHostController) {
    val context = LocalContext.current
    var dropdownMenuExpanded by remember { mutableStateOf(false) }
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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        dropdownMenuExpanded = true
                    }) {
                        Icon(Icons.Filled.MoreVert, "todoModifyDelete")
                    }

                    DropDownMenuSample(context, dropdownMenuExpanded) {
                        dropdownMenuExpanded = false
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        content = {
            TodoItemContent(padding = it)
        }
    )
}

@Composable
fun TodoItemContent(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "To do title", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 8.dp, 0.dp),
            text = "end date",
            fontSize = 16.sp,
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 0.dp, 0.dp),
            text = "To do description",
            fontSize = 20.sp
        )
    }
}

@Composable
fun TodoItem(navController: NavHostController) {
    TodoItemTopAppBar(navController = navController)
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
            TodoItem(navController = rememberNavController())
        }
    }
}