package com.example.myproject
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_log.*
import kotlinx.android.synthetic.main.fragment_log.view.*
import kotlinx.android.synthetic.main.fragment_log.view.et_pass
import kotlinx.android.synthetic.main.fragment_sign.*
import kotlinx.android.synthetic.main.fragment_sign.et_pass
import kotlinx.android.synthetic.main.fragment_sign.view.*

class LogFragment : Fragment() {
    private lateinit var comm: Communicator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_log, container, false)

        comm=requireActivity() as Communicator
        v.findViewById<Button>(R.id.bt_login).setOnClickListener {
         if(v.et_mail.text.toString() == "aditi.swami@neologicx.com" && v.et_pass.text.toString() == "happy") {
              Toast.makeText(requireContext(), "Logged In  successfully", Toast.LENGTH_SHORT).show()
             val intent = Intent (requireContext(), HomeActivity::class.java)
             startActivity(intent)
          } else {
             Toast.makeText(requireContext(), "Try again", Toast.LENGTH_SHORT).show()
         }

        }
        v.findViewById<TextView>(R.id.tv_forget).setOnClickListener {
            comm.passDataCom(v.et_mail.text.toString())
           /* var mFragmentforget: Fragment? = null
            mFragmentforget = ForgetPFragment()

            val fragmentManager: FragmentManager = parentFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragmentforget).commit()*/
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