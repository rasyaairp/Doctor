package com.mochrasya.doctorrasya

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import com.mochrasya.doctorrasya.Model.DoctorsModel

class editt : AppCompatActivity() {
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
    private lateinit var btnUpdate: Button

    private lateinit var doctorId: String // Simpan ID dokter di variabel
    private val database = FirebaseDatabase.getInstance("https://doctorrasya-default-rtdb.firebaseio.com/")
    private val doctorRef = database.getReference("Doctors")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editt)

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
        btnUpdate = findViewById(R.id.btnUpdate)

        // Load Doctor ID from Intent
        doctorId = intent.getStringExtra("DOCTOR_ID") ?: ""
        if (doctorId.isEmpty()) {
            Toast.makeText(this, "Invalid doctor ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadDoctorData(doctorId)

        // Update Button Click Listener
        btnUpdate.setOnClickListener {
            updateDoctorData()
        }
    }

    private fun loadDoctorData(doctorId: String) {
        doctorRef.child(doctorId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val doctor = snapshot.getValue(DoctorsModel::class.java)
                if (doctor != null) {
                    etName.setText(doctor.Name)
                    etAddress.setText(doctor.Address)
                    etBiography.setText(doctor.Biography)
                    etCost.setText(doctor.Cost)
                    etDate.setText(doctor.Date)
                    etExperience.setText(doctor.Expriense)
                    etMobile.setText(doctor.Mobile)
                    etPatients.setText(doctor.Patiens)
                    etPicture.setText(doctor.Picture)
                    etRating.setText(doctor.Rating)
                    etSpecial.setText(doctor.Special)
                    etTime.setText(doctor.Time)
                    etLocation.setText(doctor.Location)
                } else {
                    Toast.makeText(this, "Invalid data format in Firebase", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Doctor not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateDoctorData() {
        // Siapkan data yang diperbarui
        val updatedDoctor = mapOf(
            "Name" to etName.text.toString(),
            "Address" to etAddress.text.toString(),
            "Biography" to etBiography.text.toString(),
            "Cost" to etCost.text.toString(),
            "Date" to etDate.text.toString(),
            "Expriense" to etExperience.text.toString(),
            "Mobile" to etMobile.text.toString(),
            "Patiens" to etPatients.text.toString(),
            "Picture" to etPicture.text.toString(),
            "Rating" to etRating.text.toString(),
            "Special" to etSpecial.text.toString(),
            "Time" to etTime.text.toString(),
            "Location" to etLocation.text.toString()
        )

        // Update Firebase
        doctorRef.child(doctorId).updateChildren(updatedDoctor).addOnSuccessListener {
            Toast.makeText(this, "Doctor data updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to update doctor data", Toast.LENGTH_SHORT).show()
        }
    }
}
