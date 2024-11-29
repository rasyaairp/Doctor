package com.mochrasya.doctorrasya

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mochrasya.doctorrasya.Activity.MainActivity

class login : AppCompatActivity() {

    // Referensi Firebase
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Deklarasi View
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Inisialisasi Firebase Database
        database = FirebaseDatabase.getInstance("https://doctorrasya-default-rtdb.firebaseio.com/").getReference("Users")

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                saveUserToDatabase(email, password, etEmail, etPassword)
            }
        }
    }

    private fun saveUserToDatabase(email: String, password: String, etEmail: EditText, etPassword: EditText) {
        // Buat ID unik untuk setiap pengguna
        val userId = database.push().key ?: return

        // Buat objek pengguna
        val user = User(email, password)

        // Simpan data ke Firebase
        database.child(userId).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "User saved successfully", Toast.LENGTH_SHORT).show()
                    etEmail.text.clear()
                    etPassword.text.clear()
                    startActivity(Intent(this@login ,MainActivity::class.java))
                } else {
                    Toast.makeText(this, "Failed to save user: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
