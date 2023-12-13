package com.example.myapplication


import android.content.Context
import android.view.ViewTreeObserver
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.example.myapplication.EditTextViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import com.google.accompanist.systemuicontroller.rememberSystemUiController
//import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    navController:NavController ,
    fireBaseViewModel: FireBaseViewModel
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(0xFFD54DC5),
            darkIcons = false
        )
    }
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.scrollTo(scrollState.maxValue)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.size(200.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                val email = remember {
                    EditTextViewModel()
                }
                // crate a edit text for a email
                OutlinedTextField(
                    value = email.changeText.value,
                    onValueChange = {
                        email.changeText.value = it
                    },
                    placeholder = {
                        Text(text = "Email")
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        textColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 50.dp)
                        .padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))

                val visibility = remember {
                    mutableStateOf(false)
                }
                val visible = if (!visibility.value) { // icon for  appear  the password according to variable visibility
                    remember {
                        mutableStateOf(Icons.Rounded.VisibilityOff)
                    }
                } else {
                    remember {
                        mutableStateOf(Icons.Rounded.Visibility)
                    }
                }
                val showPassword = if (!visibility.value) { //for show the password according to variable visible
                    remember {
                        mutableStateOf(PasswordVisualTransformation())
                    }
                } else {
                    remember {
                        mutableStateOf(VisualTransformation.None)
                    }
                }
                //create a object for password for EditTextViewModel
                val password = remember {
                    EditTextViewModel()
                }
                // crate a edit text for a password
                OutlinedTextField(
                    value = password.changeText.value,
                    onValueChange = {
                        password.changeText.value = it
                    },
                    placeholder = {

                        Text(text = "Password")

                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        textColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    trailingIcon = {
                        // icon for visibility of password
                        IconButton(
                            onClick = { visibility.value = !visibility.value },
                        ) {
                            Icon(
                                imageVector = visible.value,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    },
                    visualTransformation = showPassword.value
                )
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 50.dp)
                        .padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))

                Button(
                    onClick = {
                         fireBaseViewModel.signIn(email.changeText.value, password.changeText.value, navController)

                    },
                    modifier = Modifier
                        .border(
                            brush = Brush.horizontalGradient(
                                colorStops = arrayOf(
                                    Pair(0.2f, Color(0xFF6C14BC)),
                                    Pair(0.7f, Color(0xFFE53DD1))
                                )
                            ),
                            width = 2.dp,
                            shape = RoundedCornerShape(18.dp)
                        )
                        .width(200.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(text = "login" , color = Color.Black)
                }

            }
//
            }
        }
    }





@Composable
fun rememberImeState() : State<Boolean> {
    val imeState = remember {
        mutableStateOf(false)
    }
    val view = LocalView.current
    DisposableEffect(key1 = view){
        val listener = ViewTreeObserver.OnGlobalLayoutListener{
            val isKeybordOpen = ViewCompat.getRootWindowInsets(view)?.isVisible(WindowInsetsCompat.Type.ime())?:true
            imeState.value = isKeybordOpen
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return imeState
}



