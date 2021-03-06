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
import kotlinx.android.synthetic.main.fragment_forget_p.view.*



class ForgetPFragment : Fragment(){
    private var inputText: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val v= inflater.inflate(R.layout.fragment_forget_p, container, false)
        inputText = arguments?.getString("input_txt")
        v.et_email.text = inputText

   v.findViewById<Button>(R.id.bt_submit).setOnClickListener {
         Toast.makeText(requireContext(),"Email send to register email id",Toast.LENGTH_SHORT).show()
         }
        v.findViewById<TextView>(R.id.tv_login).setOnClickListener {
            var mFragmentlogin: Fragment? = null
            mFragmentlogin = LogFragment()
            val fragmentManager: FragmentManager = parentFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragmentlogin).commit()
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

