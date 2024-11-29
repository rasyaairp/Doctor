package com.mochrasya.doctorrasya

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class create : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etAddress: EditText
    private lateinit var etBiography: EditText
    private lateinit var etCost: EditText
    private lateinit var etDate: EditText
    private lateinit var etExperience: EditText
    private lateinit var etMobile: EditText
    private lateinit var etPatients: EditText
    private lateinit var etPicture: EditText
    private lateinit var etRating: EditText
    private lateinit var etSpecial: EditText
    private lateinit var etTime: EditText
    private lateinit var etLocation: EditText
    private lateinit var btnSubmit: Button
    val database = FirebaseDatabase.getInstance("https://doctorrasya-default-rtdb.firebaseio.com/")
    val doctorRef = database.getReference("Doctors")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        // Initialize Views
        etName = findViewById(R.id.etName)
        etAddress = findViewById(R.id.etAddress)
        etBiography = findViewById(R.id.etBiography)
        etCost = findViewById(R.id.etCost)
        etDate = findViewById(R.id.etDate)
        etExperience = findViewById(R.id.etExperience)
        etMobile = findViewById(R.id.etMobile)
        etPatients = findViewById(R.id.etPatients)
        etPicture = findViewById(R.id.etPicture)
        etRating = findViewById(R.id.etRating)
        etSpecial = findViewById(R.id.etSpecial)
        etTime = findViewById(R.id.etTime)
        etLocation = findViewById(R.id.etLocation)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            addDoctorToFirebase()
        }
    }

    private fun addDoctorToFirebase() {

        // Create a unique ID using Firebase's push() method
        val id = doctorRef.push().key

        if (id != null) {
            val doctor = mapOf(
                "Id" to id,
                "Name" to etName.text.toString(),
                "Address" to etAddress.text.toString(),
                "Biography" to etBiography.text.toString(),
                "Cost" to etCost.text.toString(),
                "Date" to etDate.text.toString(),
                "Expriense" to etExperience.text.toString(), // Ensure Expriense is sent as Int
                "Mobile" to etMobile.text.toString(),
                "Patiens" to etPatients.text.toString(),
                "Picture" to etPicture.text.toString(),
                "Rating" to etRating.text.toString(),
                "Special" to etSpecial.text.toString(),
                "Time" to etTime.text.toString(),
                "Location" to etLocation.text.toString()
            )

            // Save the doctor data
            doctorRef.child(id).setValue(doctor).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Dokter berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity
                } else {
                    // Handle exception
                    val exception = task.exception
                    if (exception != null) {
                        Log.e("Firebase Error", exception.message ?: "Unknown error")
                        Toast.makeText(this, "Gagal menambahkan dokter!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Gagal membuat ID unik!", Toast.LENGTH_SHORT).show()
        }
    }
}