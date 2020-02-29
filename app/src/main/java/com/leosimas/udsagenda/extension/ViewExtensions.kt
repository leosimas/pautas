package com.leosimas.udsagenda.extension

import android.content.Context
import android.view.View
import androidx.annotation.StringRes

fun Context.getString(@StringRes resId: Int?): String? {
    if (resId == null) return null
    return getString(resId)
}

fun View.gone() { visibility = View.GONE  }

fun View.visible() { visibility = View.VISIBLE  }