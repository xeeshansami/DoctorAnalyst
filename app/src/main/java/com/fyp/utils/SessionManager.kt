package com.fyp.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hbl.hblaccountopeningapp.network.models.request.base.historyRequest


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
    fun setHistory(value: historyRequest?) {
        val gson = Gson()
        val json = gson.toJson(value)
        val editor: SharedPreferences.Editor = this.sp!!.edit()
        editor.putString(Constant.HISTORY, json)
        editor.commit()
    }

    fun getHistory(): historyRequest? {
        var companyList = historyRequest()
        val json = Gson().toJson(companyList)
        if ( this.sp!! != null) {
            val gson = Gson()
            val string: String = this.sp!!.getString(Constant.HISTORY, json).toString()
            val type = object : TypeToken<historyRequest?>() {}.type
            companyList = gson.fromJson(string, type)
            return companyList
        }
        return companyList
    }
}