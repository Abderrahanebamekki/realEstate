package com.example.myapplication


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitTicketView(

     viewModel : TicketViewModel

) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(1) }



    Column (
        verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally
        ){
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Problem") }

        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )


        Button(onClick = {
            viewModel.submitTicket(
                Ticket(title, description, priority, false,false)
            )
        }) {
            Text("Submit Ticket")
        }

        }


    }
