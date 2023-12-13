package com.example.myapplication

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NavigationViewMode : ViewModel() {
    val screen : MutableState<Activity> = mutableStateOf(Activity.HomePage)
}