package com.fyp.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.fyp.R
import com.fyp.interfaces.iOnBackPressed
import kotlinx.android.synthetic.main.fragment_signup.*
import java.text.SimpleDateFormat
import java.util.*

class FragmentUpdate() : Fragment() ,iOnBackPressed{
    val myCalendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openDateDialog()
    }

    private fun openDateDialog() {
        dateOfBirthTv!!.setOnClickListener {
            var datePicker =
                activity?.let {
                    DatePickerDialog(
                        it,
                        R.style.DialogTheme,
                        date,
                        myCalendar[Calendar.YEAR],
                        myCalendar[Calendar.MONTH],
                        myCalendar[Calendar.DAY_OF_MONTH]
                    )
                }
//            datePicker?.getDatePicker()?.setMaxDate(System.currentTimeMillis())
            datePicker?.show()
        }
    }

    var date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        myCalendar.add(Calendar.DATE, 0);
        // Set the Calendar new date as minimum date of date picker
        //val myFormat = "dd-MMM-yyyy" //In which you need put here
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateOfBirthTv.setText(sdf.format(myCalendar.time))
    }
    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        if (navController.currentDestination?.id != R.id.fragmentUpdate) {
            Log.i("onBackPress", "Not Up Finish All Fragment")
            requireActivity().finish()
            return true
        } else {
            Log.i("onBackPress", "Up")
            navController.popBackStack()
            return true
        }
    }

}