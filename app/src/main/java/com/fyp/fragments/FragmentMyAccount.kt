package com.fyp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.interfaces.iOnBackPressed
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_rehabilation.*
import kotlinx.android.synthetic.main.fragment_rehabilation.gaitAndBalTv
import kotlinx.android.synthetic.main.fragment_rehabilation.mobTraTv
import kotlinx.android.synthetic.main.fragment_rehabilation.upperLibRehTv


class FragmentMyAccount : Fragment(), View.OnClickListener, iOnBackPressed {
    var list = ArrayList<String>()
    var myView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_account, container, false)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        updateAnAccountTv.setOnClickListener(this)
        changePasswordTv.setOnClickListener(this)
        logoutTv.setOnClickListener(this)
        addQuestInRv()
    }

    private fun addQuestInRv() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.updateAnAccountTv -> {
                findNavController().navigate(R.id.action_fragmentMyAccount_to_fragmentUpdate)
            }R.id.changePasswordTv -> {
                findNavController().navigate(R.id.action_fragmentMyAccount_to_fragmentChangePassword)
            }R.id.logoutTv -> {
                Toast.makeText(activity,"Log out",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        return if (navController.currentDestination?.id != R.id.fragmentRehabilitation) {
            Log.i("onBackPress", "Not Up Finish All Fragment")
            requireActivity().finish()
            true
        } else {
            Log.i("onBackPress", "Up")
            navController.popBackStack()
            true
        }
    }
}