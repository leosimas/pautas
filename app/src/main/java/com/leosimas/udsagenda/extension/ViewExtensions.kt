package com.leosimas.udsagenda.extension

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout

open class TextChangeWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}


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

fun Context.clearErrorsWhenTextChange(vararg textLayouts: TextInputLayout) {
    textLayouts.filter { it.editText != null }.forEach {
        it.editText?.addTextChangedListener(object : TextChangeWatcher() {
            override fun afterTextChanged(s: Editable?) {
                it.error = null
            }
        })
    }
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun Button.enableWhenAllFilled(vararg editTexts: EditText?) {
    val list = editTexts.filterNotNull()
    val funCheckAll = {
        this.isEnabled = !list.any { it.text.toString().isBlank() }
    }
    list.forEach {
        it.addTextChangedListener( object : TextChangeWatcher() {
            override fun afterTextChanged(s: Editable?) {
                funCheckAll()
            }
        })
    }
}

fun EditText?.setDoneAction(listener : ()->Unit) {
    if (this == null) return
    this.imeOptions = EditorInfo.IME_ACTION_DONE
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            listener.invoke()
            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }
}
