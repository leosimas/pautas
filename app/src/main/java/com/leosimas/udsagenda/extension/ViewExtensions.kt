package com.leosimas.udsagenda.extension

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


fun Context.getString(@StringRes resId: Int?): String? {
    if (resId == null) return null
    return getString(resId)
}

fun Context.toastShort(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.gone() { visibility = View.GONE  }

fun View.visible() { visibility = View.VISIBLE  }
