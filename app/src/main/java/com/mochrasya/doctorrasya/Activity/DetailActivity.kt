package com.mochrasya.doctorrasya.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.mochrasya.doctorrasya.Model.DoctorsModel
import com.mochrasya.doctorrasya.databinding.ActivityDetailBinding
import com.mochrasya.doctorrasya.editt

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: DoctorsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundle()

        binding.btndelete.setOnClickListener {
            val doctorId = item.Id // Menggunakan ID dari objek `item`
            if (!doctorId.isNullOrEmpty()) {
                val database = FirebaseDatabase.getInstance("https://doctorrasya-default-rtdb.firebaseio.com/")
                val doctorRef = database.getReference("Doctors").child(doctorId)

                doctorRef.removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Dokter berhasil dihapus", Toast.LENGTH_SHORT).show()
                        finish() // Kembali ke aktivitas sebelumnya setelah penghapusan berhasil
                    } else {
                        Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "ID Dokter tidak valid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object") ?: run {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.apply {
            specialTxt.text = item.Special
            patiensTxt.text = item.Patiens
            bioTxt.text = item.Biography
            addressTxt.text = item.Address
            timeTxt.text = item.Time
            dateTxt.text = item.Date

            experiensTxt.text = "${item.Expriense} Years"
            ratingTxt.text = "${item.Rating}"
            backBtn.setOnClickListener { finish() }

            websiteBtn.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(item.Site)
                startActivity(i)
            }

            messageBtn.setOnClickListener {
                val uri = Uri.parse("smsto:${item.Mobile}")
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                intent.putExtra("sms_body", "the SMS text")
                startActivity(intent)
            }

            makeBtn.setOnClickListener {
                val editIntent = Intent(this@DetailActivity, editt::class.java)
                editIntent.putExtra("DOCTOR_ID", item.Id) // Kirimkan ID dokter
                startActivity(editIntent)
            }

            callBtn.setOnClickListener {
                val uri = "tel:${item.Mobile.trim()}"
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(uri))
                startActivity(intent)
            }

            directionBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.Location))
                startActivity(intent)
            }

            shareBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, item.Name)
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "${item.Name} ${item.Address} ${item.Mobile}"
                )
                startActivity(Intent.createChooser(intent, "Choose one"))
            }
        }
    }
}
