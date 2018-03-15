package com.simbirsoft.itplace.efa.huntersnet.utilities

import android.app.Activity
import android.util.Log
import com.simbirsoft.itplace.efa.huntersnet.BuildConfig

/**
 * Created by iClub on 15.03.2018.
 * Расширение для отладки и логирования приложения
 */

fun Activity.logd(message: String) {
    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
}