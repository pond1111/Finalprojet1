package com.example.crudapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudapp.databinding.ActivityReadBinding
import com.google.firebase.database.*

class ReadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")

        binding.searchButton.setOnClickListener {
            val searchVehicleNumber: String = binding.searchMessage.text.toString()
            if (searchVehicleNumber.isNotEmpty()) {
                readData(searchVehicleNumber)
            } else {
                Toast.makeText(this, "Please enter the vehicle number", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this@ReadActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun readData(vehicleNumber: String) {
        databaseReference.child(vehicleNumber).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val ownerMessage = dataSnapshot.child("ownerMessage").value.toString()
                    val dateMessage = dataSnapshot.child("dateMessage").value.toString()
                    val feelingMessage = dataSnapshot.child("feelingMessage").value.toString()

                    binding.searchMessage.text.clear()
                    binding.readOwnerMessage.text = ownerMessage
                    binding.readDate.text = dateMessage
                    binding.readFeelingMessage.text = feelingMessage

                    Toast.makeText(this@ReadActivity, "Results Found", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ReadActivity, "Vehicle number does not exist", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ReadActivity, "Failed to read value.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
