package com.example.myapplication

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class SharingSystem () {

     private var _filePicker : ActivityResultLauncher<Intent>? = null
    var filePicker : ActivityResultLauncher<Intent>?
        get(){return _filePicker}
        set (value) {
            _filePicker = value
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
                    // File data saved successfully to the database
                }
                .addOnFailureListener {
                    // Handle any errors
                }
        }
    }


    public  fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "/" // Set the MIME type or file extension you want to pick (e.g., "image/*" for images, "application/pdf" for PDF)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        filePicker?.launch(Intent.createChooser(intent, "Select File"))
    }






}