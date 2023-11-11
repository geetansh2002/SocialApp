package com.example.socialapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity(), PostAdapter.OnDataChanges {
    private lateinit var adapter:PostAdapter
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showProgressBar()
        val query = db.collection("newPost").orderBy("currentTime", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<Post>()
            .setQuery(query, Post::class.java)
            .build()
        adapter= PostAdapter(options,this)
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter=adapter
       val addPost=findViewById<FloatingActionButton>(R.id.addPost)
        addPost.setOnClickListener{
            val intent=Intent(this,CreatePost::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }
    private fun showProgressBar() {
        val progressbar=findViewById<ProgressBar>(R.id.progressbar)
        progressbar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        val progressbar=findViewById<ProgressBar>(R.id.progressbar)
        progressbar.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun datachange() {
        hideProgressBar()
    }
}
