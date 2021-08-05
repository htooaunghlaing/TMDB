package com.app.tmdb.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences


@SuppressLint("CommitPrefEdits")
class PrefManager(context: Context) {
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var _context: Context = context

    // shared pref mode
    private var PRIVATE_MODE = 0
    var getFirstTimeLaunch: Boolean
        get() = pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
        set(isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
            editor.commit()
        }

    companion object {
        // Shared preferences file name
        private const val PREF_NAME = "com.app.tmdb"
        private const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
}