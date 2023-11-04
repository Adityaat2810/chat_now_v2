package com.example.chat_now_v2.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.chat_now_v2.databinding.FragmentLoginFragBinding
import com.example.chat_now_v2.userList
import com.google.firebase.auth.FirebaseAuth

class login_frag : Fragment() {

    private lateinit var binding: FragmentLoginFragBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginFragBinding.inflate(inflater, container, false)
        val view = binding.root

        mAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            loginUser(email, password)
        }

        return view
    }

    private fun loginUser(emaill: String, passwordd: String) {
        if (emaill.isNotEmpty() && passwordd.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(emaill, passwordd)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {

                        val verification = mAuth.currentUser?.isEmailVerified
                        if(verification==true){
                            Toast.makeText(
                                requireContext(),
                                "Authentication success",
                                Toast.LENGTH_SHORT
                            ).show()
                            // After successful signup



                            startActivity(Intent(requireContext(),userList::class.java))

                        }else{

                            Toast.makeText(
                                requireContext(),
                                "Please verify your email",
                                Toast.LENGTH_SHORT
                            ).show()

                        }


                    } else {
                        // Login failed
                        val exception = task.exception
                        if (exception != null) {
                            Log.e(
                                "Authentication Error",
                                "Failed to log in: ${exception.message}",
                                exception
                            )
                            Toast.makeText(
                                requireContext(),
                                "Authentication failed: ${exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.e("Authentication Error", "Failed to log in: Unknown error")
                            Toast.makeText(
                                requireContext(),
                                "Authentication failed: Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }
    }


}
