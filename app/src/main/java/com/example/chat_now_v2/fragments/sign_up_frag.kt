package com.example.chat_now_v2.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import com.example.chat_now_v2.Profile
import com.example.chat_now_v2.R
import com.example.chat_now_v2.dataClasses.user
import com.example.chat_now_v2.databinding.FragmentLoginFragBinding
import com.example.chat_now_v2.databinding.FragmentSignUpFragBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class sign_up_frag : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var binding: FragmentSignUpFragBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpFragBinding.inflate(inflater, container, false)
        val view = binding.root



        mAuth = FirebaseAuth.getInstance()


        binding.signUp.setOnClickListener {
            val name = binding.editTextTextPersonName.text.toString()
            val email= binding.email.text.toString()
            val password = binding.password.text.toString()

            signUp(name,email,password)


        }

        return view
    }

    private fun signUp(name: String,emaill: String, passwordd: String) {
        if (emaill.isNotEmpty() && passwordd.isNotEmpty()) {
            mAuth.createUserWithEmailAndPassword(emaill, passwordd)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // User created successfully

                        mAuth.currentUser?.sendEmailVerification()
                            ?.addOnSuccessListener {
                                Toast.makeText(requireContext(), "Please verify your email", Toast.LENGTH_SHORT).show()
                                addUserToDatabase(name,emaill, mAuth.currentUser?.uid!!)


                                val intent = Intent(requireContext(), Profile::class.java)
                                intent.putExtra("name", name)
                                intent.putExtra("email", emaill)
                                intent.putExtra("uid", mAuth.currentUser?.uid) // Use mAuth.currentUser?.uid here

// Start the Profile activity
                                startActivity(intent)



                            }
                            ?.addOnFailureListener{
                               Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
                            }

                    } else {
                        // Failed to create user
                        Toast.makeText(requireContext(), "User creation failed", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            // Handle case where email or password is empty
            Toast.makeText(requireContext(), "Email or password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }


    private fun addUserToDatabase(name: String, email: String, uid: String) {

        mDbRef= FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(user(name,email, uid,"","","",""))

    }

}