package com.example.study_choi.screen

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.study_choi.ui.theme.Study_choiTheme
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoItemTopAppBar(navController: NavHostController) {
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
            AddTodoItemContent(padding = it)
        }
    )
}

@Composable
fun AddTodoItemContent(padding: PaddingValues) {
    val openTimeDialog = remember { mutableStateOf(false) }
    val openDateDialog = remember { mutableStateOf(false) }

    val deadLineDate = remember { mutableStateOf("날짜") }
    val deadLineTime = remember { mutableStateOf("시간") }

    val titleTextState = remember { mutableStateOf("") }
    val descriptionTextState = remember { mutableStateOf("") }

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
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(16.dp, 0.dp)
        ) {
            Text(text = "추가")
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
fun AddTodoItem(navController: NavHostController) {
    AddTodoItemTopAppBar(navController = navController)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadLineTimePickerDialog(
    openTimeDialog: MutableState<Boolean>,
    deadLineTime: MutableState<String>
) {
    val timePickerState = rememberTimePickerState()

    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 12.dp)
            ),
        onDismissRequest = { openTimeDialog.value = false }
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.LightGray.copy(alpha = 0.3f)
                )
                .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimePicker(state = timePickerState)

            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { openTimeDialog.value = false }) {
                    Text(text = "Cancel")
                }

                TextButton(
                    onClick = {
                        openTimeDialog.value = false
                        if (timePickerState.minute < 10) {
                            deadLineTime.value =
                                "${timePickerState.hour}:0${timePickerState.minute}"
                        } else {
                            deadLineTime.value =
                                "${timePickerState.hour}:${timePickerState.minute}"
                        }
                    }
                ) {
                    Text(text = "OK")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlineDatePickerDialog(
    openDateDialog: MutableState<Boolean>,
    deadLineDate: MutableState<String>
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }
    DatePickerDialog(
        onDismissRequest = { openDateDialog.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    openDateDialog.value = false
                    val selectedDate = datePickerState.selectedDateMillis?.let {
                        convertMillisToDate(it)
                    } ?: ""
                    deadLineDate.value = selectedDate
                },
                enabled = confirmEnabled.value
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    openDateDialog.value = false
                }
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
    return formatter.format(Date(millis))
}

@Preview
@Composable
fun previewAddTodo() {
    Study_choiTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AddTodoItem(navController = rememberNavController())
        }
    }
}