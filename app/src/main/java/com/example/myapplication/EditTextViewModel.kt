package com.example.myapplication

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class EditTextViewModel : ViewModel() {

        private var _changeText = mutableStateOf("")


        var changeText: MutableState<String> = _changeText
        get() = field                     // getter
        set(value) { field = value }      // setter

    }