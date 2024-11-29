package com.mochrasya.doctorrasya.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.FirebaseDatabase
import com.mochrasya.doctorrasya.Activity.DetailActivity
import com.mochrasya.doctorrasya.Model.DoctorsModel
import com.mochrasya.doctorrasya.databinding.ViewholderNearbyDoctorBinding

class NearDoctorsAdapter(private val items: MutableList<DoctorsModel>) :
    RecyclerView.Adapter<NearDoctorsAdapter.Viewholder>() {

    private var context: Context? = null

    class Viewholder(val binding: ViewholderNearbyDoctorBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        return Viewholder(
            ViewholderNearbyDoctorBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val doctor = items[position]
        holder.binding.nameTxt.text = doctor.Name
        holder.binding.specialTxt.text = doctor.Special
        holder.binding.costTxt.text = doctor.Cost

        Glide.with(holder.itemView.context)
            .load(doctor.Picture)
            .apply(RequestOptions().transform(CenterCrop()))
            .into(holder.binding.img)

        // Detail view
        holder.binding.root.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("object", doctor)
            context?.startActivity(intent)
        }

        // Delete button logic

    }

    override fun getItemCount(): Int = items.size
}
