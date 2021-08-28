package com.app.tmdb.util

import android.app.Activity
import android.view.WindowManager

class Utality {

    fun setStatusBarColor(activity: Activity, color: Int) {
        activity.window
        activity.window
            .addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity.window.statusBarColor = color
    }
}