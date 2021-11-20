package com.example.myproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager

class SignFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =inflater.inflate(R.layout.fragment_sign, container, false)
        v.findViewById<Button>(R.id.bt_signup).setOnClickListener {
            Toast.makeText(requireContext(),"Signup", Toast.LENGTH_SHORT).show()
        }
        v.findViewById<TextView>(R.id.tv_login).setOnClickListener {
            var mFragmentlogin: Fragment? = null
            mFragmentlogin = LogFragment()
            val fragmentManager: FragmentManager = parentFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragmentlogin).commit()
        }

        return v
    }

}