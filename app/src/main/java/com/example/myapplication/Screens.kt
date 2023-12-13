package com.example.myapplication

sealed class Activity(route : String){
    object HomePage:Activity("HomePage")
    object chating:Activity("chating")
    object Profile:Activity("Ticket")
    object MyProj:Activity("MyProj")


}