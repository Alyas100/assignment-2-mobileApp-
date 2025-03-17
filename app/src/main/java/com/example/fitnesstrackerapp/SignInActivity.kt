package com.example.fitnesstrackerapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    // Declaring variable needed for elements in xml layout
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Initialize UI components
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signInButton = findViewById(R.id.signInButton)
        signUpButton = findViewById(R.id.signUpButton)


        // Sign In Button Click Listener
        signInButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Check if field are empty and shows error
            // Else call the authentication method and pass the username and password that entered
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            } else {
                authenticateUser(username, password)
            }
        }


        // Sign Up Button Click Listener
        // If user didnt have an account, they press the 'sign up' button
        // It then navigate to Sign Up Page
        signUpButton.setOnClickListener {
            // Creates intent that tell android system to move from current activity
            // Contain two argument inside the intent which referred to current activity(this), and target activity(SignUpActivity::class.java)
            val intent = Intent(this, SignUpActivity::class.java)

            // Executes the intent and starts the target activity
            startActivity(intent)
        }
    }


    // This function below is just checking the username and password that entered by user, and check it with the existing key-value of collection of document in firebase fireauth (assume we use fireauth service)
    private fun authenticateUser(username: String, password: String) {

        // Initialize an instance of Firebase Authentication
        val auth = FirebaseAuth.getInstance()

        // Attempt to sign in user using firebase authentication
        // Takes two argument from user which are username and password
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->

                // Shows toast message if user succesfully login
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                    // Then, the user will be get redirected to the main menu page
                    val intent = Intent(this, MainMenuActivity::class.java)

                    // Executing the intent and start the target activity
                    startActivity(intent)

                    // Finish the current activity
                    // this also prevent important as it prevent user from going back to the 'sign in screen', if they pressed 'back' button in main menu
                    finish()
                } else {
                    // If login failed, show error message
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
    }
}