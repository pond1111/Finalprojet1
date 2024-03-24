package com.example.crudapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudapp.databinding.ActivityDeleteBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deleteButton.setOnClickListener {
            val vehicleNumber = binding.deleteMessage.text.toString()
            if (vehicleNumber.isNotEmpty()){
                deleteData(vehicleNumber)
            } else {
                Toast.makeText(this,"Please enter vehicle number",Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonBack.setOnClickListener {

            val intent = Intent(this@DeleteActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun deleteData(vehicleNumber: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        databaseReference.child(vehicleNumber).removeValue().addOnSuccessListener {
            binding.deleteMessage.text.clear()
            Toast.makeText(this,"Deleted",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Unable to Delete",Toast.LENGTH_SHORT).show()
        }
    }

}