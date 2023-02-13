package com.stadium.andballs.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.stadium.andballs.SharedPref
import com.stadium.andballs.utils.Constants.EN
import com.stadium.andballs.utils.Constants.KEY_LANGUAGE
import com.stadium.andballs.utils.Constants.KEY_MUSIC
import com.stadium.andballs.utils.Constants.KEY_VIBR


fun Fragment.vibrator() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                this.requireContext()
                    .getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vib = vibratorManager.defaultVibrator
            vib.vibrate(VibrationEffect.createOneShot(100, 1))
        } else {
            val vib = this.requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vib.vibrate(70)
        }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

fun Fragment.saveBoolean(key: String, value: Boolean) {
    SharedPref(this.requireContext()).saveBoolean(key, value)
}

fun Fragment.getBoolean(key: String): Boolean {
    return SharedPref(this.requireContext()).getBoolean(key)
}

fun Fragment.saveString(key: String, value: String) {
    SharedPref(this.requireContext()).saveString(key, value)
}

fun Fragment.getString(key: String): String {
    val language = SharedPref(this.requireContext()).getString(key)
    if (language != null) {
        return language
    }
    return ""
}

