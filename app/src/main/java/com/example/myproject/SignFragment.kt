package com.example.myproject

import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_sign.*
import kotlinx.android.synthetic.main.fragment_sign.view.*


class SignFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =inflater.inflate(R.layout.fragment_sign, container, false)
        v.findViewById<Button>(R.id.bt_signup).setOnClickListener {
            if (v.et_mail_add.text.toString()=="" ){
                v.et_mail_add.error = "Enter Email address"
            }
           if(IsEmailvalid(v.et_mail_add.text.toString())) {
                v.et_mail_add.error = ("Please Enter Valid Email")
            }
           if (v.et_confirm_pass.text.toString()=="" || v.et_pass.text.toString()=="") {
                v.et_pass.error = "Enter Password"
                v.et_confirm_pass.error = "Enter Password "
            }else{
                if (v.et_pass.text.toString() == v.et_confirm_pass.text.toString()) {
                    Toast.makeText(requireContext(), "Password same", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Check Password", Toast.LENGTH_SHORT).show()
                }
            }

            //Toast.makeText(requireContext(), "Signup", Toast.LENGTH_SHORT).show()
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

    private fun IsEmailvalid(toString: String): Boolean {
        return EMAIL_ADDRESS.matcher(et_mail_add.text.toString()).matches()

    }

}