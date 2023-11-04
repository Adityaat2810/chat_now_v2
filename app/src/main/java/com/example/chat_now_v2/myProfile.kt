package com.example.chat_now_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.example.chat_now_v2.databinding.ActivityMyProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class myProfile : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        val uid = mAuth.currentUser?.uid

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
                Toast.makeText(this@myProfile, "can not get data ", Toast.LENGTH_SHORT).show()
            }
        })


        databaseReference.child("email").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val email = dataSnapshot.getValue(String::class.java)
                if (email != null) {
                    binding.userEmail.text = email
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors here if needed
                Toast.makeText(this@myProfile, "can not get data ", Toast.LENGTH_SHORT).show()
            }
        })
        databaseReference.child("phoneNumber").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val phoneNumber = dataSnapshot.getValue(String::class.java)
                if (phoneNumber != null) {
                    binding.userMobile.text = phoneNumber
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors here if needed
                Toast.makeText(this@myProfile, "can not get data ", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@myProfile, "can not get data ", Toast.LENGTH_SHORT).show()
            }
        })
 databaseReference.child("gender").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val gender = dataSnapshot.getValue(String::class.java)
                if (gender != null) {
                    binding.userGender.text = gender
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors here if needed
                Toast.makeText(this@myProfile, "can not get data ", Toast.LENGTH_SHORT).show()
            }
        })

        databaseReference.child("imageUri").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val imageUri = dataSnapshot.getValue(String::class.java)
                if (imageUri != null) {
                    Glide.with(this@myProfile).load(imageUri)
                        .into(binding.userImage)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors here if needed
                Toast.makeText(this@myProfile, "can not get data ", Toast.LENGTH_SHORT).show()
            }
        })






    }
}
