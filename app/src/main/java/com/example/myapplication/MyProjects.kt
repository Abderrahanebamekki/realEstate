package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.sql.Blob

@Composable
fun MyProject(navController: NavController, viewModel: NavigationViewMode){
    val context = LocalContext.current
    val openWebPage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
    }
    var progress = remember { mutableStateOf(0.5f) }
    Column {
        Card(
            modifier = Modifier.height(200.dp).fillMaxWidth().padding(horizontal = 10.dp, vertical = 30.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFA5DEE1)
            )
        ) {
            Row {
                Column {
                    Text("Building Progress", modifier = Modifier.padding(start = 10.dp, top = 10.dp))
                    Text(
                        "Estmated",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(start = 40.dp, top = 10.dp)
                    )
                }

                Button(
                    onClick = {
                            val url = "http://192.168.139.25:5000/" // Replace with your URL
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            openWebPage.launch(intent)
                                     },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.padding(top = 10.dp, start = 60.dp)
                ) {
                    Text("3d Apprt")
                }
            }

            LinearProgressIndicator(
                progress = progress.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            )
        }
        LazyColumn {
            item {
                Row {
                    Card(
                        modifier = Modifier.height(250.dp).width(200.dp).padding(horizontal = 8.dp, vertical = 15.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFD8FDFF)
                        )
                    ) {
                        Image(painter = painterResource(R.drawable.house) , "" , modifier = Modifier.height(200.dp).fillMaxWidth())
                        Text("Building", color = Color.Black)
                    }

                    Card(
                        modifier = Modifier.height(250.dp).width(200.dp).padding(horizontal = 8.dp, vertical = 15.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFD8FDFF)
                        )
                    ) {
                        AsyncImage(
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data("it.apart_img")//"https://images.pexels.com/photos/5727885/pexels-photo-5727885.jpeg?auto=compress&cs=tinysrgb&w=600"
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                        )
                        Text("Building", color = Color.Black)
                    }
                }
                Row {
                    Card(
                        modifier = Modifier.height(250.dp).width(200.dp).padding(horizontal = 8.dp, vertical = 15.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFD8FDFF)
                        )
                    ) {
                        AsyncImage(
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data("it.apart_img")//"https://images.pexels.com/photos/5727885/pexels-photo-5727885.jpeg?auto=compress&cs=tinysrgb&w=600"
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                        )
                        Text("Building", color = Color.Black)
                    }

                    Card(
                        modifier = Modifier.height(250.dp).width(200.dp).padding(horizontal = 8.dp, vertical = 15.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFD8FDFF)
                        )
                    ) {
                        AsyncImage(
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data("it.apart_img")//"https://images.pexels.com/photos/5727885/pexels-photo-5727885.jpeg?auto=compress&cs=tinysrgb&w=600"
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                        )
                        Text("Building", color = Color.Black)
                    }
                }
                Button(
                    onClick = {} ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD8FDFF)
                    ) ,
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                ){
                    Text("Tracking payment" , color = Color.Black)
                }
            }
            }

        }

}