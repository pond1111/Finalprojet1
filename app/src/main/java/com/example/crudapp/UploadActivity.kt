package com.example.crudapp

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudapp.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val ownerName = binding.uploadOwnerMessage.text.toString()
            val vehicleBrand = binding.uploadDateMessage.text.toString()
            val vehicleRTO = binding.uploadFeelingMessage.text.toString()
            val vehicleNumber = binding.uploadMessage.text.toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
            val vehicleData = MessageData(ownerName, vehicleBrand, vehicleRTO, vehicleNumber)
            databaseReference.child(vehicleNumber).setValue(vehicleData).addOnCanceledListener {
                binding.uploadOwnerMessage.text.clear()
                binding.uploadDateMessage.text.clear()
                binding.uploadFeelingMessage.text.clear()
                binding.uploadMessage.text.clear()
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }
            showToastAndRedirect("Updated", 750)
        }

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this@UploadActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun showToastAndRedirect(message: String, delayMillis: Long) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        android.os.Handler(Looper.getMainLooper()).postDelayed({ // Using the constructor that takes Looper as parameter
            startActivity(Intent(this@UploadActivity, MainActivity::class.java))
            finish()
        }, delayMillis)
    }
}