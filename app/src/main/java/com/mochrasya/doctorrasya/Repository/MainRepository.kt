package com.mochrasya.doctorrasya.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mochrasya.doctorrasya.Model.DoctorsModel
import android.util.Log

class MainRepository {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun load(): LiveData<MutableList<DoctorsModel>> {
        val listData = MutableLiveData<MutableList<DoctorsModel>>()
        val ref = firebaseDatabase.getReference("Doctors")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<DoctorsModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(DoctorsModel::class.java)
                    item?.let { lists.add(it) }
                }
                listData.postValue(lists) // Use postValue to update from a background thread
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainRepository", "Database error: ${error.message}") // Log the error
            }
        })

        return listData
    }
}
