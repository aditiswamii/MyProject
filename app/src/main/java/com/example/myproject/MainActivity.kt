package com.example.myproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var mFragment: Fragment? = null
        mFragment = LoginFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.frameLayout, mFragment).commit()
       val  btn_login=findViewById<Button>(R.id.btn_login)
        val  btn_signup=findViewById<Button>(R.id.btn_signup)
        val  btn_forget=findViewById<Button>(R.id.btn_forget)


        btn_login.setOnClickListener {
            var mFragmentlogin: Fragment? = null
            mFragmentlogin = LoginFragment()
            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragmentlogin).commit()
        }
        btn_signup.setOnClickListener {
            var mFragmentsign: Fragment? = null
            mFragmentsign = SignupFragment()
            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragmentsign).commit()
        }
        btn_forget.setOnClickListener {
            var mFragmentforget: Fragment? = null
            mFragmentforget = ForgetFragment()
            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragmentforget).commit()
        }

    }
}
