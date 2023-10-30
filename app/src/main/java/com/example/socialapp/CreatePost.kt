package com.example.socialapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreatePost : AppCompatActivity() {
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val db= Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        val typeBox=findViewById<EditText>(R.id.typeBox)
        val postButton=findViewById<Button>(R.id.postButton)
        postButton.setOnClickListener{
            val post=typeBox.text.toString().trim()
            if (post.isNotEmpty()){
                getUsername(post)
            }
        }
    }
    private fun getUsername(post: String) {
        val currentUser = currentUser
        currentUser?.let { user ->
            val userId = user.uid
            val userRef = db.collection("users").document(userId)

            userRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val username = documentSnapshot.getString("username") ?: "Default Username"
                        val userImage = documentSnapshot.getString("userImage") ?: "Default UserImage"
                        addPost(post, username, userImage)
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreError", "Error fetching user data", e)
                    Toast.makeText(this, "Error fetching user data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun addPost(post:String,username:String,userImage:String){
       if (currentUser!=null){
           val userId = currentUser.uid
           val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
               Date()
           )
           val newPost= hashMapOf(
               "post" to post,
               "username" to username,
               "userImage" to userImage,
               "uid" to userId,
               "currentTime" to currentTime,
           )

           db.collection("newPost").add(newPost)
               .addOnSuccessListener { documentReference ->
                   val message = "Post Created with ID: " + documentReference.id
                   Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                   finish()
               }
               .addOnFailureListener { e ->
                   Log.e("FirestoreError", "Firestore operation failed", e)
                   Toast.makeText(this, " ${e.message}", Toast.LENGTH_SHORT).show()
               }
       }
    }
}