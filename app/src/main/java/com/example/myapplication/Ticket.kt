package com.example.myapplication

data class Ticket(
    val title: String,
    val description: String,
    val priority: Int,
    var isResolved : Boolean,
    val accepted : Boolean)