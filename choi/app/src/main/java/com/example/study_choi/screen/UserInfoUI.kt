package com.example.study_choi.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.study_choi.UserViewModel
import com.example.study_choi.ui.theme.Study_choiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoTopAppBar(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxWidth(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "회원 정보")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            UserInfoContent(it, navController, userViewModel)
        }
    )
}

@Composable
fun UserInfoContent(
    padding: PaddingValues,
    navController: NavHostController,
    userViewModel: UserViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "이름",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 0.dp, 0.dp),
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 8.dp, 0.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Spacer(
                Modifier
                    .matchParentSize()
                    .background(Color.LightGray)
            )
            Text(
                text = userViewModel.name.value,
                modifier = Modifier
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "ID",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 0.dp, 0.dp),
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 8.dp, 0.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Spacer(
                Modifier
                    .matchParentSize()
                    .background(Color.LightGray)
            )
            Text(
                text = userViewModel.loginId.value,
                modifier = Modifier
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "비밀번호",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 0.dp, 0.dp),
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 8.dp, 0.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Spacer(
                Modifier
                    .matchParentSize()
                    .background(Color.LightGray)
            )
            Text(
                text = userViewModel.password.value,
                modifier = Modifier
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ClickableText(
                text = AnnotatedString("비밀번호 변경"),
                onClick = {
                    navController.navigate(AllUI.ChangePassword.name)
                },
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
            )
        }

    }
}

@Composable
fun UserInfo(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    UserInfoTopAppBar(navController, userViewModel)
}

@Preview
@Composable
fun preUserInfo() {
    Study_choiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            UserInfo(
                navController = rememberNavController(),
                userViewModel = UserViewModel()
            )
        }
    }
}