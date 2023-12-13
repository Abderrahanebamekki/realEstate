package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ChatScreen(navController: NavController, viewModel: NavigationViewMode,fireBaseViewModel : FireBaseViewModel, onSendMessage: (String) -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        UserInput(onSendMessage = onSendMessage)
        val messagelist = remember { mutableStateOf<List<String>>(emptyList()) }
        fireBaseViewModel.listenForMessages(){
            Log.d("1111", "ChatScreen: ${it.get(3)}")
            messagelist.value = it
        }
        // Message List
                MessageList(messages = messagelist.value)

        }
        // User Input

    }

@Composable
fun MessageList(messages: List<String>) {
    LazyColumn(modifier = Modifier.height(700.dp)) {
        items(messages) { message ->
            MessageItem(message = message)
        }
    }
}

@Composable
fun MessageItem(message: String) {
    Text(
        text = message,
        modifier = Modifier
            .padding(8.dp)
            .padding(16.dp)
            .fillMaxWidth(),
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInput(onSendMessage: (String) -> Unit) {
    val messageText = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        TextField(
            value = messageText.value,
            onValueChange = {
                messageText.value = it
            },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            placeholder = { Text("Type a message...") },
            singleLine = true,
        )

        Button(
            onClick = {
                onSendMessage(messageText.value)
                messageText.value = ""
            },
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text("Send")
        }
    }
}
