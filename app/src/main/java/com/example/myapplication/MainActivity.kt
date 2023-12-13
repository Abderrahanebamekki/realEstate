package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class MainActivity : ComponentActivity() {


    val systemSharing = SharingSystem()


    private val pickFile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { uri ->
                // Replace 'uploaderName' with the actual name of the uploader
                uploadFileToFirebaseStorage(uri, "uploaderName")
            }
        }
    }




    private fun uploadFileToFirebaseStorage(fileUri: Uri, uploaderName: String) {
        val storage = Firebase.storage
        val storageRef = storage.reference

        val fileName = "file_${UUID.randomUUID()}" // Change the filename as needed
        val fileReference = storageRef.child("uploads/$fileName")
        val uploadTask = fileReference.putFile(fileUri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Get the download URL of the uploaded file
            fileReference.downloadUrl.addOnSuccessListener { uri ->
                // Once uploaded, save the file URL and uploader's name to Firebase Realtime Database
                saveFileDataToDatabase(uri.toString(), uploaderName)
            }.addOnFailureListener {
                // Handle any errors obtaining the download URL
            }
        }.addOnFailureListener { exception ->
            // Handle unsuccessful uploads
        }
    }


    private fun saveFileDataToDatabase(fileUrl: String, uploaderName: String) {
        val database = Firebase.database
        val filesRef = database.getReference("files") // Reference to your Firebase Realtime Database node for files

        val fileId = filesRef.push().key // Generate a unique key for the file
        val fileData = HashMap<String, Any>()
        fileData["url"] = fileUrl
        fileData["uploader"] = uploaderName

        if (fileId != null) {
            filesRef.child(fileId).setValue(fileData)
                .addOnSuccessListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                .addOnFailureListener {
                    // Handle any errors
                }
        }

    }



    fun sendFilePicker(){
        systemSharing.filePicker = pickFile
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "/" // Set the MIME type or file extension you want to pick (e.g., "image/*" for images, "application/pdf" for PDF)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        pickFile.launch(Intent.createChooser(intent, "Select File"))
    }




    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getIntent().getIntExtra("something",0)==1){
                   openFilePicker()

        }else


        sendFilePicker()


        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
//                    FirebaseDatabase.getInstance().getReference("chats")
                    val fireBaseViewModel = FireBaseViewModel()
//                         fireBaseViewModel.listenForMessages()
//                    Log.d("TAG", "onCreate: ${fireBaseViewModel.messagesList} ")
//                    ChatScreen(fireBaseViewModel){
//                        fireBaseViewModel.sendMessage("abdelkoudous.lebcir@gmail.com" ,"doudous6666@gmail.com",it)
//                    }
//                    fireBaseViewModel.trakinPay()
//                    TrakingPay(fireBaseViewModel)
                    val navController = rememberNavController()

                    Navigation(navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}