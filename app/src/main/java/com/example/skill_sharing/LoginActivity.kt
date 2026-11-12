package com.example.skill_sharing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.checkerframework.checker.units.qual.s

class LoginActivity : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var pass: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        email = findViewById<EditText>(R.id.email)
        pass = findViewById<EditText>(R.id.password)

    }

    fun startFeed(view: View) {
        var sEmail= email.text.toString()
        var sPass= pass.text.toString()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(sEmail, sPass)
            .addOnSuccessListener { result ->
                val uid = result.user!!.uid
                val email = result.user!!.email
                FirebaseDatabase.getInstance().getReference("users").
                child(uid).get().addOnSuccessListener {
                    snapshot ->
                    var username =snapshot.getValue(String::class.java)
                    val intent = Intent(this, FeedActivity::class.java)
                    intent.putExtra("Id",uid)
                    intent.putExtra("Email",email)
                    intent.putExtra("Username",username)
                    startActivity(intent)



                }


            }
    }
}