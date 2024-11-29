package com.mochrasya.doctorrasya

// Data Model
data class User(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "" // Disarankan: gunakan hash untuk password
)