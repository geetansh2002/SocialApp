package com.example.socialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = Firebase.auth
        val button1=findViewById<Button>(R.id.button1)
        val emailEditText=findViewById<EditText>(R.id.email)
        val passwordEditText=findViewById<EditText>(R.id.password)
        button1.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Email and Password is required",Toast.LENGTH_SHORT).show()
            }
            else {
                signIn(email, password)
            }
        }
        val button2=findViewById<TextView>(R.id.button2)
        button2.setOnClickListener{
            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser=auth.currentUser
        updateUI(currentUser)
    }
    private fun signIn(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    val exception = task.exception
                    Toast.makeText(this, "Authentication failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
       if (user!=null){
           val intent= Intent(this,MainActivity::class.java)
           startActivity(intent)
       }
    }
}