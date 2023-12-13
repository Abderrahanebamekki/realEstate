package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController) {

    val viewModel = NavigationViewMode()
    val fireBaseViewModel = FireBaseViewModel()
    NavHost(navController = navController, startDestination = "loginPage" ){
        composable("HomePage"){
            HomePage( navController ,viewModel = viewModel, context = LocalContext.current)
        }
        composable("loginPage"){
            LoginPage( navController, fireBaseViewModel)
        }
        composable("chating"){
            ChatScreen(navController = navController , viewModel = viewModel , fireBaseViewModel = fireBaseViewModel ){
                fireBaseViewModel.sendMessage(fireBaseViewModel.userId,fireBaseViewModel.otherUserId,it)
            }
        }
//        composable("Profile"){
//            Profile(navController = navController , viewModel)
//        }
        composable("MyProj"){
            MyProject(navController = navController, viewModel = viewModel)
        }

        composable(route = "webView"){

        }

    }
}