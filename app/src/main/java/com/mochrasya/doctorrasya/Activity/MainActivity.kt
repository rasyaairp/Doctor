package com.mochrasya.doctorrasya.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mochrasya.doctorrasya.Adapter.NearDoctorsAdapter
import com.mochrasya.doctorrasya.R
import com.mochrasya.doctorrasya.ViewModel.MainViewModel
import com.mochrasya.doctorrasya.create
import com.mochrasya.doctorrasya.databinding.ActivityMainBinding
import com.mochrasya.doctorrasya.login

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel=MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNearByDoctor()
    }

    private fun initNearByDoctor() {
        binding.apply {
            progressBar.visibility= View.VISIBLE
            viewModel.loadDoctors().observe(this@MainActivity, Observer {
                topView.layoutManager=
                    LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
                topView.adapter=NearDoctorsAdapter(it)
                progressBar.visibility=View.GONE
            })
            tambah.setOnClickListener {
                startActivity(Intent(this@MainActivity, create::class.java))
            }
        }
    }
}