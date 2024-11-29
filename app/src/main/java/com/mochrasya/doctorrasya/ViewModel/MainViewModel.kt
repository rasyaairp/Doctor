package com.mochrasya.doctorrasya.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mochrasya.doctorrasya.Model.DoctorsModel
import com.mochrasya.doctorrasya.Repository.MainRepository

class MainViewModel():ViewModel() {

    private val repository=MainRepository()

    fun loadDoctors():LiveData<MutableList<DoctorsModel>>{
        return repository.load()
    }
}