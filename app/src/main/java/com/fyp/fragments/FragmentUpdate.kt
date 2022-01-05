package com.fyp.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.interfaces.iOnBackPressed
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.fragment_signup.firstNameTv
import kotlinx.android.synthetic.main.fragment_signup.mobileTv
import kotlinx.android.synthetic.main.fragment_update.*
import java.util.*


class FragmentUpdate : Fragment(), iOnBackPressed, View.OnClickListener {
    val myCalendar = Calendar.getInstance()
//    private var mDatabase: DatabaseReference? = null
    private var sessionManager: SessionManager? = null
//    var firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        var mobile = sessionManager!!.getStringVal(Constant.MOBILE)
        mobileTv.setText(mobile)
        mobileTv.isEnabled=false
        mobileTv.alpha=0.5f
//        getDataFromFirebase()
    }

    fun init() {
        updateBtn.setOnClickListener(this)
//        firebaseAuth = FirebaseAuth.getInstance()
        sessionManager = SessionManager(activity as ActivityDashboard)
    }


    fun getDataFromFirebase() {
        var check = false
        var fName = ""
        var lName = ""
        var mobile = sessionManager!!.getStringVal(Constant.MOBILE)
        var age = ""
        val progressDialog =
            ProgressDialog.show(
                activity,
                "Please wait",
                "Fetching your profile...",
                true
            )
//        val rootRef = FirebaseDatabase.getInstance().reference
//        val dbRef = rootRef.child("upwork-f2a18-default-rtdb").child("RegisteredUsers")
//        dbRef!!.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (data in dataSnapshot.children) {
//                        if (data.child("mobile").value == mobile) {
//                            var map: MutableMap<*, *>? = data.getValue(
//                                MutableMap::class.java
//                            )
//                            fName = map!!.get("fName").toString();
//                            lName =map!!.get("lName").toString();
//                            mobile = map!!.get("mobile").toString();
//                            age = map!!.get("age").toString();
//                            check = true
//                            break
//                        } else {
//                            check = false
//                        }
//                    }
//                    if (check) {
//                        firstNameTv.setText(fName)
//                        lastName.setText(lName)
//                        mobileTv.setText(mobile)
//                        AgeTv.setText(age)
//                        progressDialog.dismiss()
//                    } else {
//                        Toast.makeText(
//                            activity,
//                            resources.getString(R.string.login_err), Toast.LENGTH_LONG
//                        )
//                            .show()
//                        progressDialog.dismiss()
//                    }
//                }
//                for (data in dataSnapshot.children) {
//
//                    progressDialog.dismiss()
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                progressDialog.dismiss()
//            }
//        })
    }

    private fun updateDB() {
        try {
            updatePwd()
        } catch (ex: Exception) {
            Toast.makeText(
                activity,
                ex.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun updatePwd() {
        var key = ""
        var check = false
        var progressDialog = ProgressDialog.show(
            activity,
            resources.getString(R.string.please_wait),
            resources.getString(R.string.updating),
            true
        )
        var fName = firstNameTv.text.toString().trim()
        var lName = lastName.text.toString().trim()
        var mobile = sessionManager!!.getStringVal(Constant.MOBILE)
        var Age = AgeTv.text.toString().trim()
        var mobile2 = mobileTv.text.toString().trim()
//        val rootRef = FirebaseDatabase.getInstance().reference
//        val dbRef = rootRef.child("upwork-f2a18-default-rtdb").child("RegisteredUsers")
//        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (d in dataSnapshot.children) {
//                        if (d.child("mobile").value == mobile) {
//                            check = true
//                            key = d.key.toString()
//                            break
//                        } else {
//                            check = false
//                        }
//                    }
//                    if (check) {
//                        sessionManager!!.setStringVal(Constant.MOBILE, mobile)
//                        var taskMap: MutableMap<String, Any> = HashMap()
//                        taskMap["fName"] = fName
//                        taskMap["lName"] = lName
//                        taskMap["age"] = Age
//                        taskMap["mobile"] = mobile2
//                        dbRef.child(key).updateChildren(taskMap)
//                        findNavController().navigateUp()
//                        progressDialog.dismiss()
//                        Toast.makeText(
//                            activity as ActivityDashboard,
//                            resources.getString(R.string.update_succcess), Toast.LENGTH_LONG
//                        )
//                    } else {
//                        Toast.makeText(
//                            activity,
//                            resources.getString(R.string.update_failed), Toast.LENGTH_LONG
//                        )
//                            .show()
//                        progressDialog.dismiss()
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                progressDialog.dismiss()
//            } //onCancelled
//        })
    }

    private fun validation(): Boolean {
        var fName = firstNameTv.text.toString().trim()
        var lName = lastName.text.toString().trim()
        var mobile = mobileTv.text.toString().trim()
        var Age = AgeTv.text.toString().trim()
        return if (fName.isNullOrEmpty()) {
            firstNameTv.error = resources.getString(R.string.fname_err)
            firstNameTv.requestFocus()
            false
        } else if (lName.isNullOrEmpty()) {
            lastName.error = resources.getString(R.string.lname_err)
            lastName.requestFocus()
            false
        } else if (mobile.isNullOrEmpty()) {
            mobileTv.error = resources.getString(R.string.mob_err)
            mobileTv.requestFocus()
            false
        } else if (mobile.length < 11) {
            mobileTv.error =
                resources.getString(R.string.mobile_digits_err)
            mobileTv.requestFocus()
            false
        } else if (Age.isNullOrEmpty()) {
            AgeTv.error = resources.getString(R.string.age_err)
            AgeTv.requestFocus()
            false
        } else {
            true
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.updateBtn -> {
                if (validation()) {
                    updateDB()
                }
            }
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}