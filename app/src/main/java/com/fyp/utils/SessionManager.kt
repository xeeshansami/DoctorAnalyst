package com.fyp.utils

import android.content.Context
import android.content.SharedPreferences


class SessionManager(context: Context) {
    var context: Context? = null
    var sp: SharedPreferences? = null
    init {
        this.context = context
        getSharedPref()
    }
    private fun getSharedPref() {
        val PREFS_NAME = this.context!!.packageName
        this.sp= context!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    fun setStringVal(key: String?, value: String?) {
        if (key != null) {
            val edit = this.sp!!.edit()
            edit.putString(key, value)
            edit.commit()
        }
    }
    fun getStringVal(key: String?): String? {
        return this.sp!!.getString(key, "")
    }
    fun setIntVal(key: String?, value: Int?) {
        if (key != null) {
            val edit = this.sp!!.edit()
            edit.putInt(key, value!!)
            edit.commit()
        }
    }
    fun getIntVal(key: String?): Int? {
        return this.sp!!.getInt(key, 0)
    }
}