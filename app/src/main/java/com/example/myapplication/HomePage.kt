package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController , viewModel: NavigationViewMode, context: Context){
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(0xFF0b2666),
            darkIcons = false
        )
    }
    Scaffold(
        bottomBar = {
     NavigationBar(viewModel)
        } ,
        topBar = {
            Row (modifier = Modifier.background(Color(0xFFA9E8EB)).clickable {  }){
                Icon(icon= R.drawable.baseline_message_24){
                    navController.navigate("chating")
                }
                Spacer(modifier = Modifier.width(160.dp))
                Icon(icon= R.drawable.baseline_upload_file_24){
                    val sharingSystem = SharingSystem()
                    sharingSystem.openFilePicker()
                    val intent = Intent(context,MainActivity::class.java)
                    intent.putExtra("something",1)
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it).background(Color.White).fillMaxSize()){
            when( viewModel.screen.value) {
                is Activity.HomePage -> {
                    Column (
                    ){
                        val fireBaseViewModel = FireBaseViewModel()
                        Search()
                        Offers(fireBaseViewModel)
                    }
                }
                is Activity.MyProj -> {
                   MyProject(navController,viewModel)
                }
                else->{
                    val  viewModel = TicketViewModel()
                    SubmitTicketView(viewModel)
                }
            }
        }

    }

}


@Composable
fun NavigationBar(viewModel: NavigationViewMode) {
    Row(
        modifier = Modifier.background(color = Color(0xFFA9E8EB) )
            .height(80.dp) ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon = R.drawable.baseline_construction_24){
            viewModel.screen.value = Activity.MyProj
        }
        Icon(R.drawable.baseline_home_24){
            viewModel.screen.value = Activity.HomePage
        }
        Icon(icon = R.drawable.baseline_person_24){
            viewModel.screen.value = Activity.Profile
        }
    }
}


@Composable
fun RowScope.Icon(
    @DrawableRes icon :  Int ,
    onClick : ()->Unit
) {
    Icon(
        painterResource(icon),
        contentDescription = null,
        tint = Color.Black ,
        modifier = Modifier
            .size(45.dp)
            .weight(1f)
            .clickable {
                onClick()
            }
    )
}
//@Composable
//fun Offers(fireBaseViewModel : FireBaseViewModel){
//    fireBaseViewModel.fetchDataFromFirebase()
//    when(val result = fireBaseViewModel.response.value){
//        is DataState.Loading ->{
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
//                CircularProgressIndicator(color = Color(0xFFE53DD1))
//            }
//        }
//        is DataState.Success -> {
//            CardView(fireBaseViewModel)
//        }
//        is DataState.Failure -> {
//            Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center){
//                Text(
//                    text = result.message,
//                    fontSize = MaterialTheme.typography.labelMedium.fontSize
//                )
//            }
//        }
//        else ->{
//            CardView(fireBaseViewModel)
//        }
//    }
//}
@Composable
fun Offers(fireBaseViewModel : FireBaseViewModel){
    val listAp = remember { mutableStateOf<List<Apartment>>(emptyList()) }
    fireBaseViewModel.fetchDataFromFirebase { tempList ->
        listAp.value = tempList
    }
    CardView(listAp.value)
}

@Composable
fun CardView(tempList : List<Apartment> ){
    LazyColumn {
        items(tempList){
            Card(
                modifier = Modifier.height(300.dp).fillMaxWidth().padding(horizontal = 15.dp , vertical = 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor =Color(0xFFD8FDFF)
                )
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(it.apart_img)//"https://images.pexels.com/photos/5727885/pexels-photo-5727885.jpeg?auto=compress&cs=tinysrgb&w=600"
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                )
                Text(it.description)
                Text(it.price.toString() + "DA" , textAlign = TextAlign.Right , color = Color.Black )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search() {
    val changeText = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        OutlinedTextField(
            value = changeText.value,
            onValueChange = {
                changeText.value = it
            } ,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(30.dp)),
            placeholder = {
                Text(
                    text = "sreach",
                    modifier = Modifier
                        .alpha(12f)
                        .align(Alignment.End),
                    color = Color.Gray,
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                textAlign = TextAlign.Right
            ),
            singleLine = true ,
            leadingIcon = {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .alpha(12f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color(0xFF5AA2B8)
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .alpha(12f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon",
                        tint = Color(0xFF5AA2B8)
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onSearch = {

                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                cursorColor = Color.Black,
                textColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Black
            ),
            shape = RoundedCornerShape(30.dp) ,

            )
    }
}


@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@Composable
@ComposableInferredTarget("")
fun RealEstateXJobCategories() {
    Column(
        modifier = Modifier.padding(all = 8.dp).fillMaxSize()
    ) {
        // Welcome to RealEstateX text
        Text(
            text = "Welcome to RealEstateX",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Select job categories text
        Text(
            text = "Select one or more job categories that match your skills and interests",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        val categorys = listOf(category("Real Estate", Icons.Filled.Search),category("Property Manager", Icons.Filled.Search), category("Construction", Icons.Filled.Search), category("Architect", Icons.Filled.Search), category("Interior Designer", Icons.Filled.Search))
        // Job categories grid
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(categorys) { index  , category ->
                // Job category chip
                Card (
                    modifier = Modifier.height(150.dp).fillMaxWidth().clickable {
                        when (category.text){
                            "Real Estate" -> {

                            }
                            "Property Manager" -> {

                            }
                            "Construction" -> {}
                            "Architect" -> {}
                            "Interior Designer" -> {}
                        }
                    }
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(category.icon , contentDescription = null)
                        Text(category.text , fontSize = 20.sp)
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))

            }
        }
    }
}

data class category(var text: String, var icon: ImageVector)

@Composable
fun Categorys() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            Category(R.drawable.house1 , "New Listing")
            Category(R.drawable.house1,"New Project")
        }
        Row {
            Category(R.drawable.house1,"open House")
            Category(R.drawable.house1,"Reduces House")
        }
    }
}

@Composable
fun Category(image : Int , description : String){
    Box(
        modifier = Modifier.size(150.dp).clickable {  },
    ){
        Image(
            painter = painterResource(image) ,
            contentDescription = "" ,
            contentScale = ContentScale.Crop
        )
        Text(description)
    }

}

//@Composable
//fun TypeAppart(){
//    val selectedButtonIndex = remember { mutableStateOf(0) }
//        LazyRow {
//            item {
//                Button(
//                    onClick = {},
//                ) {
//                    Text("Real Estate")
//                }
//            }
//            itemsIndexed(buttonTypeAppar) { index, item ->
//                Button(
//                    onClick = {
//                        selectedButtonIndex.value = index
//                    },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = if (index == selectedButtonIndex.value) Color.Black else  Color.White ,
//                        contentColor = Color.Red
//                    )
//                ) {
//                    Text(item.name, color = if (index == selectedButtonIndex.value) Color.White else Color.Black)
//                }
//            }
//        }
//}
