package com.leosimas.udsagenda.dao

import android.content.Context
import com.google.gson.Gson
import com.leosimas.udsagenda.bean.User

object SharedPref {

    private const val KEY_LOGGED_USER = "user"
    private val GSON = Gson()

    private fun getPref(context : Context)
            = context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)

    fun saveUser(context : Context, user : User?) {
        getPref(context).edit()
            .putString(KEY_LOGGED_USER, GSON.toJson(user))
            .apply()
    }

    fun loadUser(context : Context) : User? {
        val json = getPref(context).getString(KEY_LOGGED_USER, null)
            ?: return null
        return GSON.fromJson(json, User::class.java)
    }

}