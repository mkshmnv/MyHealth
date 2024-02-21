package com.mkshmnv.myhealth

import android.util.Log

object Logger {
    fun logcat(message: String, tag: String? = "") {
        Log.d("Logger $tag", message)
    }
}