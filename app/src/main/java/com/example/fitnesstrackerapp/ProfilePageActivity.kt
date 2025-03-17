package com.example.fitnesstrackerapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfilePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        val editProfileButton = findViewById<Button>(R.id.editProfileButton)
        val backButton = findViewById<Button>(R.id.backButton)

        editProfileButton.setOnClickListener {

            // goes to 'Profile edit' page that can enable user to edit their profile

        }

        backButton.setOnClickListener {
            finish() // Closes the ProfileActivity and goes back to the previous screen
        }

    }
}