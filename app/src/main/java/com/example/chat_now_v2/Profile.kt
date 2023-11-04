package com.example.chat_now_v2

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class Profile : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var ageTextView: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var gender: TextView
    private lateinit var addData: Button

    private var picUri: Uri? = null

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        picUri = it
        imageView.setImageURI(picUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val uid = intent.getStringExtra("uid")

        imageView = findViewById(R.id.profilePic)
        ageTextView = findViewById(R.id.age)
        phoneNumber = findViewById(R.id.mobileNumber)
        gender = findViewById(R.id.gender)
        addData = findViewById(R.id.createProfile)

        imageView.setOnClickListener {
            selectImage.launch("image/*")
        }

        addData.setOnClickListener {
            FirebaseAuth.getInstance().currentUser?.let { it1 -> uploadImage(it1.uid) }
        }
    }

    private fun uploadImage(uid: String) {
        val storageRef = FirebaseStorage.getInstance().reference
            .child("profile")
            .child(uid)
            .child("profile.jpg")

        storageRef.putFile(picUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl
                    .addOnSuccessListener {
                        val imageUri = it
                        val phoneNUmber = phoneNumber.text.toString()
                        val age = ageTextView.text.toString()
                        val userGender = gender.text.toString()
                        updateUserProfile(uid, imageUri, phoneNUmber, age, userGender)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }
    private fun updateUserProfile(
        uid: String,
        imageUri: Uri?,
        phoneNumber: String,
        age: String,
        gender: String
    ) {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("user").child(uid)
        val updatedUserData = HashMap<String, Any?>()

        // Convert Uri to string if it's not null
        updatedUserData["imageUri"] = imageUri?.toString()

        // Ensure non-null values for phoneNumber, age, and gender
        updatedUserData["phoneNumber"] = phoneNumber
        updatedUserData["age"] = age
        updatedUserData["gender"] = gender

        // Update the user's data in the database
        databaseReference.updateChildren(updatedUserData).addOnSuccessListener {
            Toast.makeText(this, "good ", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
    }

}
