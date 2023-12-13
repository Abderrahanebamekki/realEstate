package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

class TicketViewModel : ViewModel() {


        private val database = FirebaseDatabase.getInstance().reference

        fun submitTicket (ticket: Ticket) {
            // set isResolved to false by default
            ticket.isResolved = false

            // generate a push id as primary key for the ticket
            val key = database.child("tickets").push().key
            if (key == null) {
                Log.w("tagtag", "Couldn't get push key for ticket")
                return
            }

            // add ticket to database using the generated key
            database.child("tickets").child(key).setValue(ticket)
        }



}