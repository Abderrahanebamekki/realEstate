package com.example.myapplication

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import com.example.myapplication.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FireBaseViewModel : ViewModel() {
    val response : MutableState<DataState> = mutableStateOf(DataState.Empty)
    val firebaseInst = FirebaseDatabase.getInstance()
    var userId:String = "abdelkoudous.lebcir@gmail.com"
    var otherUserId:String = "doudous6666@gmail.com"
    val messagesList = mutableListOf<String>()
    val response1 : MutableState<DataState> = mutableStateOf(DataState2.Empty)
    val response2 : MutableState<DataState> = mutableStateOf(DataState3.Empty)
    val dayList = mutableListOf<String>()
    val auth = FirebaseAuth.getInstance()

    val tempList = mutableListOf<Apartment>()
    fun fetchDataFromFirebase(onDataFetched:(List<Apartment>) -> Unit){
        response.value = DataState.Loading
        firebaseInst.getReference("apartments")
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        tempList.clear()
                        for (dataSnap in snapshot.children){
                            val queItem = dataSnap.getValue(Apartment::class.java)
                            if (queItem != null && check(queItem = queItem)){
                                tempList.add(queItem)
                                Log.d("aaaa",queItem.floor_id.toString())
                            }
                        }
                        response.value = DataState.Success(tempList)
                        onDataFetched(tempList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        response.value = DataState.Failure(error.message)
                    }

                }
            )
    }
     fun sendMessage(senderEmail: String, receiverEmail: String, message: String) {

         listenForMessages {

         }
        val chatId = createChatId(senderEmail, receiverEmail)

        val messageReference = firebaseInst.getReference("chats").child(chatId).child("messages").push()
        val messageInfo = HashMap<String, Any>()
        messageInfo["senderEmail"] = senderEmail
        messageInfo["receiverEmail"] = receiverEmail
        messageInfo["message"] = message
        messageInfo["timestamp"] = ServerValue.TIMESTAMP
        messageReference.setValue(messageInfo)
            .addOnSuccessListener {
                // Message sent successfully
            }
            .addOnFailureListener {
                // Handle the error
            }
    }

    private fun createChatId(email1: String, email2: String): String {
        // Replace invalid characters in emails and create a valid path
        val sanitizedEmail1 = email1.replace(".", "_dot_")
        val sanitizedEmail2 = email2.replace(".", "_dot_")
        return if (sanitizedEmail1 < sanitizedEmail2) {
            "${sanitizedEmail1}_${sanitizedEmail2}"
        } else {
            "${sanitizedEmail2}_${sanitizedEmail1}"
        }
    }

    fun listenForMessages(onDataFetched:(List<String>) -> Unit) {
        response1.value = DataState2.Loading
        val chatId = createChatId(userId, otherUserId)
        val messageQuery = firebaseInst.getReference("chats").child(chatId).child("messages")
        val messageEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newMessagesList = mutableListOf<String>()
                for (messageSnapshot in snapshot.children) {
                    val sender = messageSnapshot.child("receiverEmail").getValue(String::class.java)
                    val receiver = messageSnapshot.child("senderEmail").getValue(String::class.java)
                    val message = messageSnapshot.child("message").getValue(String::class.java)
                    Log.d("weeeee",sender.toString())
                    Log.d("weeeeeed",receiver.toString())

                    if (message != null) {
                        if (sender.toString()=="abdelkoudous.lebcir@gmail.com")
                            newMessagesList.add("You: $message")
                        else
                            newMessagesList.add("Admin: $message")

                        Log.d("rrrr", "onDataChange:$message ")
                    }
                }
                messagesList.clear()
                messagesList.addAll(newMessagesList)
                onDataFetched(messagesList)
                response1.value = DataState2.Success(messagesList)
                Log.d("dddd", "onDataChange:${messagesList.get(4)} ")
            }

            override fun onCancelled(error: DatabaseError) {
                response1.value = DataState2.Failure(error.message)
            }
        }
        if (messageQuery != null) {
            messageQuery.addValueEventListener(messageEventListener)
        }
    }
private fun check( queItem : Apartment ) : Boolean {
        for (item in tempList){
            if (queItem == item){
                return false
            }
        }
        return true
    }

    fun trakinPay(){
        response2.value = DataState2.Loading
        val dateq = firebaseInst.getReference("PaymentDay").child("1")
         val dateEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnap in snapshot.children){
                    val queItem = dataSnap.getValue(String::class.java)
                    if (queItem != null){
                        dayList.add(queItem)
                    }
                }
                response2.value = DataState2.Success(dayList)
            }

            override fun onCancelled(error: DatabaseError) {
                response2.value = DataState2.Failure(error.message)
            }
        }
        if (dateq != null) {
            dateq.addValueEventListener(dateEvent)
        }
    }

    public  fun signIn (email: String, password: String, navController: NavController) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    navController.navigate(route = "HomePage")
                } else {
                }
            }
    }






}

