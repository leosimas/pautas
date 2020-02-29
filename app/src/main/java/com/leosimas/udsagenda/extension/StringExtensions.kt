package com.leosimas.udsagenda.extension

import android.util.Patterns

fun String?.isValidEmail() : Boolean {
    return this != null && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}