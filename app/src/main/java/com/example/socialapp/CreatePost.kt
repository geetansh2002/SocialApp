package com.example.socialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CreatePost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        val typeBox=findViewById<EditText>(R.id.typeBox)
        val postButton=findViewById<Button>(R.id.postButton)
        postButton.setOnClickListener{
            val post=typeBox.text.toString().trim()
            if (post.isNotEmpty()){

            }
        }
    }
}