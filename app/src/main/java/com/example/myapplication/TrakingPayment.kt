package com.example.myapplication


import android.os.Build
import android.text.format.Time
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrakingPay(fireBaseViewModel : FireBaseViewModel){
    when(val result = fireBaseViewModel.response2.value){
        is DataState.Loading ->{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator(color = Color(0xFFE53DD1))
            }
        }
        is DataState.Success -> {
            LazyColumn {
                items(fireBaseViewModel.dayList){
                    Row {
                        Box {
                            CircleWithDisc(color = if(LocalDate.now().format(DateTimeFormatter.ISO_DATE) == it) Color.Green else Color(0xFFE7E7E7) )
                            VerticalLine(color = if(LocalDate.now().format(DateTimeFormatter.ISO_DATE) == it) Color.Green else Color(0xFFE7E7E7))
                        }
                        Text("payment date : $it")
                    }
                }
            }
        }
        is DataState.Failure -> {
            Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center){
                Text(
                    text = result.message,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize
                )
            }
        }
        else ->{
            LazyColumn {
                items(fireBaseViewModel.dayList){
                    Row {
                        Box {
                            CircleWithDisc(color = if(LocalDate.now().format(DateTimeFormatter.ISO_DATE) == it) Color.Green else Color(0xFFE7E7E7) )
                            VerticalLine(color = if(LocalDate.now().format(DateTimeFormatter.ISO_DATE) == it) Color.Green else Color(0xFFE7E7E7))
                        }
                        Text("payment date : $it")
                    }
                }
            }
        }
    }
}

@Composable
fun CircleWithDisc(color: Color) {
    Surface (
        modifier = Modifier
            .size(70.dp)
            .background(Color.White, RectangleShape)
            .padding(16.dp)
            .clip(CircleShape)
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .height(2.dp)
                .width(2.dp)
                .background(color)
                .padding(top = 10.dp , start = 10.dp)
                .clickable {  }
        ) {}
    }
}


@Composable
fun VerticalLine(color: Color) {
    Canvas(
        modifier = Modifier
            .width(10.dp)
            .height(180.dp)
            .padding(start = 35.dp , top = 50.dp)
    ) {
        drawLine(
            color = color,
            start = Offset(size.width / 2, 0f),
            end = Offset(size.width / 2, size.height),
            strokeWidth = 2.dp.toPx() // Adjust the stroke width as needed
        )
    }
}