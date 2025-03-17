package com.example.fitnesstrackerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu) //

        // Get button references
        val btnStartWorkout = findViewById<Button>(R.id.btn_startWorkout)
        val btnViewProgress = findViewById<Button>(R.id.btn_viewProgress)
        val btnProfile = findViewById<Button>(R.id.btn_profile)
        val btnLogout = findViewById<Button>(R.id.btn_logout)

        // Navigate to Workout Activity
        btnStartWorkout.setOnClickListener {

            // goes to start workout page

        }

        // Navigate to Progress Activity
        btnViewProgress.setOnClickListener {

            // goes to view progress page

        }

        // Navigate to Profile Activity
        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfilePageActivity::class.java)
            startActivity(intent)
        }

        // Logout action (Navigate back to sign-in Screen)
        btnLogout.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)

            // This will clear the back stack to ensure all previous activitis are cleared
            // Also prevent user to go back to previous activities when user press 'back button'
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
