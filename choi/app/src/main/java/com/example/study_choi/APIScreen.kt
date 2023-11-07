package com.example.study_choi

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.study_choi.retrofit.RetrofitBuilderTourSpot
import com.example.study_choi.retrofit.TourDTO
import com.example.study_choi.retrofit.TourSpotDJ
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun APIScreen() {
    var apiText by remember { mutableStateOf("api text") }

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        androidx.compose.material3.TopAppBar (
            title = { Text("API Call") }
        )

        Spacer(modifier = Modifier.height(128.dp))

        Text(text = apiText)

        Spacer(modifier = Modifier.height(64.dp))

        Button(onClick = {
            val tsListService = RetrofitBuilderTourSpot.getInstanceFor().create(TourSpotDJ::class.java)
            val listCall = tsListService.getTourSpot()
            listCall.enqueue(object : Callback<TourDTO> {
                override fun onResponse(call: Call<TourDTO>, response: Response<TourDTO>) {
                    if (response.isSuccessful && response.code() == 200) {
                        val s = response.body()?.response?.body?.items.toString()

                        apiText = s
                    }
                }

                override fun onFailure(call: Call<TourDTO>, t: Throwable) {
                    Log.d("error_", t.stackTrace.toString())

                    apiText = t.stackTrace.toString()
                }
            })
        }) {
            Text(text = "api 불러오기")
        }

    }
}
