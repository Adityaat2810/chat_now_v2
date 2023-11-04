package com.example.chat_now_v2


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.chat_now_v2.databinding.ActivityMainBinding
import com.example.chat_now_v2.fragments.login_frag
import com.example.chat_now_v2.fragments.sign_up_frag


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(login_frag())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.login ->replaceFragment(login_frag())
                R.id.signUp ->replaceFragment(sign_up_frag())


                else ->{

                }
            }
            true


        }



    }

    private fun replaceFragment(fragment:Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()

    }
}