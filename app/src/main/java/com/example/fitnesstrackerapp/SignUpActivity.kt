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
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var signInButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance()
        // get the firestore instance
        db = FirebaseFirestore.getInstance()


        // Bind UI elements
        fullNameEditText = findViewById(R.id.fullNameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signUpButton = findViewById(R.id.signUpButton)
        signInButton = findViewById(R.id.signInButton)


        // Handle Sign Up
        signUpButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()

            } else {
                // call the function to register the user
                registerUser(fullName, email, username, password)
            }
        }

        // Navigate to Sign-In Page back
        signInButton.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



    // Function to register user
    private fun registerUser(fullName: String, email: String, username: String, password: String) {

        // This function creates a new user account using the email password that provided by the user
        auth.createUserWithEmailAndPassword(email, password)

            // Callback function that runs when the firebase request is completed
            // Task represents the result of the sign-up process
            .addOnCompleteListener(this) { task ->
                // If the creation is successful, firebase
                if (task.isSuccessful) {
                    // Gets currently authenticated user and '.uid' retrieves the unique Firebase-generated ID for the user
                    // The retireved ID then used to store user data in firestore
                    val userId = auth.currentUser?.uid

                    // Check user id and make sure it not null before storing data
                    if (userId != null) {
                        // Create a key-value pair object as document inside collection in firebase firestore and store, and save the data there
                        val user = hashMapOf(
                            "fullName" to fullName,
                            "email" to email,
                            "username" to username
                        )


                        // 'db' referrred to firebase firestore database instance
                        // it access the user's collection in firestore
                        // Using the ID we got from above to create new document inisde the collection named 'user'
                        db.collection("users").document(userId)
                            // Upload the user data which contain 'full name', 'email', 'password' to firestore
                            .set(user)

                            // this function is triggered if the user data is successfully stored in firestore.
                            .addOnSuccessListener {
                                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, SignInActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            // triggered if storing user data in firestore fails
                            .addOnFailureListener {
                                Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
                            }
                    }

                    // Handling registration failure that might be cause by email that are already exists or weak password etc
                } else {
                    Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}