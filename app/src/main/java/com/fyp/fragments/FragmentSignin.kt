package com.fyp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fyp.R
import kotlinx.android.synthetic.main.fragment_signin.*


class FragmentSignin() : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInBtn.setOnClickListener(this)
        signUpBtn.setOnClickListener(this)
        forgetPwdTxt.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.signInBtn ->{
                findNavController().navigate(R.id.action_signin_to_dashboard)
            }
            R.id.signUpBtn ->{
                findNavController().navigate(R.id.action_signin_to_signup)
            }
            R.id.forgetPwdTxt ->{
                findNavController().navigate(R.id.action_signin_to_fragmentForgetPassword)
            }
        }
    }

}