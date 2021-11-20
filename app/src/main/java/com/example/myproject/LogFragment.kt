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

class LogFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_log, container, false)
        v.findViewById<Button>(R.id.bt_login).setOnClickListener {
            Toast.makeText(requireContext(),"login", Toast.LENGTH_SHORT).show()
        }
        v.findViewById<TextView>(R.id.tv_forget).setOnClickListener {
            var mFragmentforget: Fragment? = null
            mFragmentforget = ForgetPFragment()
            val fragmentManager: FragmentManager = parentFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragmentforget).commit()
        }
        v.findViewById<TextView>(R.id.tv_signup).setOnClickListener {
            var mFragmentsign: Fragment? = null
            mFragmentsign = SignFragment()
            val fragmentManager: FragmentManager = parentFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragmentsign).commit()
        }

        return v

    }

}