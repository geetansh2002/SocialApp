package com.example.socialapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateProfileActivity : AppCompatActivity() {
    private lateinit var pickImage:ImageButton
    private  var imageUri2:Uri?=null
    private val db = Firebase.firestore
    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                val selectedImage: Uri? = data.data
                selectedImage?.let {
                    imageUri2=selectedImage
                    Glide.with(this)
                        .load(selectedImage)
                        .into(pickImage)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        val email=intent.getStringExtra("userEmail")
        pickImage=findViewById(R.id.pickImage)
        pickImage.setOnClickListener{
            imagePicker()
        }
        val userName=findViewById<EditText>(R.id.userName)
        val userName2=userName.text.toString()
        val createButton=findViewById<Button>(R.id.createButton)
        createButton.setOnClickListener{
            addUser(email,userName2,imageUri2)
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun imagePicker(){
        val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        getContent.launch(intent)
    }
    private fun addUser(email:String?,userName:String?,pickImage:Uri?){
        val user = hashMapOf(
            "email" to email,
            "username" to userName,
            "image_url" to pickImage?.toString()
        )
        db.collection("users").add(user)
            .addOnSuccessListener { documentReference ->
                val message = "Profile Created with ID: " + documentReference.id
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Firestore operation failed", e)
                Toast.makeText(this, " ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }
}