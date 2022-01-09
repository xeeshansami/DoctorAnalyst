package com.fyp.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.interfaces.iOnBackPressed
import com.fyp.network.models.response.base.BaseResponse
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager
import com.fyp.utils.ToastUtils
import com.hbl.hblaccountopeningapp.network.ResponseHandlers.callbacks.RegisterCallBack
import com.hbl.hblaccountopeningapp.network.enums.RetrofitEnums
import com.hbl.hblaccountopeningapp.network.store.HBLHRStore
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.cityTv
import kotlinx.android.synthetic.main.fragment_update.mobileTv
import kotlinx.android.synthetic.main.fragment_update.spinner
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*


class FragmentUpdate : Fragment(), iOnBackPressed, View.OnClickListener {
    val myCalendar = Calendar.getInstance()

    //    private var mDatabase: DatabaseReference? = null
    private var list = ArrayList<String>()
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
        mobileTv.isEnabled = false
        mobileTv.alpha = 0.5f
        addQuestInRv()
    }



    private fun addQuestInRv() {
        val questions = (activity as ActivityDashboard).resources!!.getStringArray(R.array.gender)
        list.clear()
        for (element in questions) {
            list.add(element)
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            activity as ActivityDashboard,
            R.layout.quest_list_view, R.id.text1,
            list
        )
        adapter.setDropDownViewResource(R.layout.quest_list_view)
        spinner.setAdapter(adapter)
    }

    fun init() {
        updateBtn.setOnClickListener(this)
//        firebaseAuth = FirebaseAuth.getInstance()
        sessionManager = SessionManager(activity as ActivityDashboard)
    }


    private fun updateUser() {
        var fName = fullNameTv.text.toString().trim()
        var city = cityTv.text.toString().trim()
        var mobile = sessionManager!!.getStringVal(Constant.MOBILE)
        var Age = AgeTv.text.toString().trim()
        var mobile2 = mobileTv.text.toString().trim()
        (activity as ActivityDashboard).globalClass?.showDialog((activity as ActivityDashboard))
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", fName)
            .addFormDataPart("age", Age)
            .addFormDataPart("gender", spinner.selectedItem.toString().trim())
            .addFormDataPart("city", city)
            .addFormDataPart("phone", mobile2)
            .build()
        HBLHRStore.instance?.updateUser(
            RetrofitEnums.URL_HBL,
            requestBody, object : RegisterCallBack {
                @SuppressLint("WrongConstant")
                override fun Success(response: BaseResponse) {
                    findNavController().navigateUp()
                    ToastUtils.showToastWith(
                        activity,
                        resources.getString(R.string.update_succcess)
                    )
                    Log.i("Counter", "2")
                }

                override fun Failure(response: BaseResponse) {
                    ToastUtils.showToastWith((activity as ActivityDashboard), response.message)
                    (activity as ActivityDashboard).globalClass?.hideLoader()
                }
            })
    }

    private fun validation(): Boolean {
        var fName = fullNameTv.text.toString().trim()
        var lName = cityTv.text.toString().trim()
        var mobile = mobileTv.text.toString().trim()
        var Age = AgeTv.text.toString().trim()
        return if (fName.isNullOrEmpty()) {
            fullNameTv.error = resources.getString(R.string.fname_err)
            fullNameTv.requestFocus()
            false
        } else if (lName.isNullOrEmpty()) {
            cityTv.error = resources.getString(R.string.city_err)
            cityTv.requestFocus()
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
                    updateUser()
                }
            }
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}