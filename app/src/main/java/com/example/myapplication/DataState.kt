package com.example.myapplication


sealed class DataState{
    class Success(val data: MutableList<Apartment>) : DataState()
    class Failure(val message : String): DataState()
    object Loading : DataState()
    object Empty :DataState()
}

sealed class DataState2{
    class Success(val data: MutableList<String>) : DataState()
    class Failure(val message : String): DataState()
    object Loading : DataState()
    object Empty :DataState()
}

sealed class DataState3{
    class Success(val data: MutableList<String>) : DataState()
    class Failure(val message : String): DataState()
    object Loading : DataState()
    object Empty :DataState()
}