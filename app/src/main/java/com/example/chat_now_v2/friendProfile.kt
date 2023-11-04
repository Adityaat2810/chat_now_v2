package com.example.chat_now_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.chat_now_v2.databinding.ActivityFriendProfileBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class friendProfile : AppCompatActivity() {
    private lateinit var binding:ActivityFriendProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFriendProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid=intent.getStringExtra("uniqueid")


        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("user").child(uid!!)

        databaseReference.child("name").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val name = dataSnapshot.getValue(String::class.java)
                if (name != null) {
                    binding.userName.text = name
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors here if needed
                Toast.makeText(this@friendProfile, "can not get data ", Toast.LENGTH_SHORT).show()
            }
        })


        databaseReference.child("email").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val email = dataSnapshot.getValue(String::class.java)
                if (email != null) {
                    binding.userEmail.text = email
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors here if needed
                Toast.makeText(this@friendProfile, "can not get data ", Toast.LENGTH_SHORT).show()
            }
        })
        databaseReference.child("phoneNumber").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val phoneNumber = dataSnapshot.getValue(String::class.java)
                if (phoneNumber != null) {
                    binding.userMobile.text = phoneNumber
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors here if needed
                Toast.makeText(this@friendProfile, "can not get data ", Toast.LENGTH_SHORT).show()
            }
        })


        databaseReference.child("age").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val age = dataSnapshot.getValue(String::class.java)
                if (age != null) {
                    binding.userAge.text = age
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors here if needed
                Toast.makeText(this@friendProfile, "can not get data ", Toast.LENGTH_SHORT).show()
            }
        })
        databaseReference.child("gender").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val gender = dataSnapshot.getValue(String::class.java)
                if (gender != null) {
                    binding.userGender.text = gender
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors here if needed
                Toast.makeText(this@friendProfile, "can not get data ", Toast.LENGTH_SHORT).show()
            }
        })

        databaseReference.child("imageUri").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val imageUri = dataSnapshot.getValue(String::class.java)
                if (imageUri != null) {
                    Glide.with(this@friendProfile).load(imageUri)
                        .into(binding.userImage)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors here if needed
                Toast.makeText(this@friendProfile, "can not get data ", Toast.LENGTH_SHORT).show()
            }
        })



    }
}