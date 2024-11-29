package com.mochrasya.doctorrasya

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    // Firebase Realtime Database Reference
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Deklarasi Views
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // Inisialisasi Firebase Database
        database = FirebaseDatabase.getInstance("https://doctorrasya-default-rtdb.firebaseio.com/").getReference("Users")

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Validasi input
            when {
                name.isEmpty() -> {
                    etName.error = "Name cannot be empty"
                    etName.requestFocus()
                }
                email.isEmpty() -> {
                    etEmail.error = "Email cannot be empty"
                    etEmail.requestFocus()
                }
                password.isEmpty() -> {
                    etPassword.error = "Password cannot be empty"
                    etPassword.requestFocus()
                }
                password.length < 6 -> {
                    etPassword.error = "Password must be at least 6 characters"
                    etPassword.requestFocus()
                }
                else -> {
                    saveUserToDatabase(name, email, password, etName, etEmail, etPassword)
                    startActivity(Intent(this@RegisterActivity, login::class.java))
                }
            }
        }
    }

    private fun saveUserToDatabase(
        name: String,
        email: String,
        password: String,
        etName: EditText,
        etEmail: EditText,
        etPassword: EditText
    ) {
        // Buat ID unik untuk setiap pengguna
        val userId = database.push().key ?: return

        // Buat objek pengguna (untuk keamanan, jangan simpan password langsung)
        val user = User(userId, name, email, password) // Password sebaiknya di-hash

        // Simpan data ke Firebase
        database.child(userId).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    etName.text.clear()
                    etEmail.text.clear()
                    etPassword.text.clear()
                } else {
                    Toast.makeText(this, "Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}


