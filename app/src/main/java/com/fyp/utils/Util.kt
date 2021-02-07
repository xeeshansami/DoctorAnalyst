package com.fyp.utils

import android.util.Patterns

class Util {
    companion object {
        fun isValidEmail(target: String?): Boolean {
            return if (target == null) {
                false
            } else {
                //android Regex to check the email address Validation
                Patterns.EMAIL_ADDRESS.matcher(target).matches()
            }
        }
    }
}