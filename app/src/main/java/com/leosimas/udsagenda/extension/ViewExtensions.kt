package com.leosimas.udsagenda.extension

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.getString(@StringRes resId: Int?): String? {
    if (resId == null) return null
    return getString(resId)
}

fun Context.toastShort(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun View.gone() { visibility = View.GONE  }

fun View.visible() { visibility = View.VISIBLE  }
