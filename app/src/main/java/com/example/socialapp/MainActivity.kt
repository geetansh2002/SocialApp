package com.example.socialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       val addPost=findViewById<FloatingActionButton>(R.id.addPost)
        addPost.setOnClickListener{
            val intent=Intent(this,CreatePost::class.java)
            startActivity(intent)
        }
    }
}