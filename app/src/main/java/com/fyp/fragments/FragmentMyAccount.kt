package com.fyp.fragments

import android.app.ProgressDialog
import android.content.Intent
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
import com.fyp.activities.ActivityDashboard
import com.fyp.activities.LogActivity
import com.fyp.interfaces.iOnBackPressed
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account.*


class FragmentMyAccount : Fragment(), View.OnClickListener, iOnBackPressed {
    var list = ArrayList<String>()
    var myView: View? = null
    private var firebaseAuth: FirebaseAuth? = null
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

    fun init() {
        firebaseAuth = FirebaseAuth.getInstance()
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
            }
            R.id.changePasswordTv -> {
                findNavController().navigate(R.id.action_fragmentMyAccount_to_fragmentChangePassword)
            }
            R.id.logoutTv -> {
                logout()
            }
        }
    }

    private fun logout() {
        val progressDialog =
            ProgressDialog.show(activity, "Please wait", "Logging out...", true)
        Toast.makeText(activity, "Log out", Toast.LENGTH_SHORT).show()
        firebaseAuth?.signOut()
        var intent = Intent(activity, LogActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        progressDialog.dismiss()
        (activity as ActivityDashboard).startActivity(intent)
        (activity as ActivityDashboard).finish()
    }

    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        return if (navController.currentDestination?.id != R.id.fragmentMyAccount) {
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