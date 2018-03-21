package com.simbirsoft.itplace.efa.huntersnet.utilities

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.simbirsoft.itplace.efa.huntersnet.BuildConfig

/**
 * Created by iClub on 15.03.2018.
 * Расширение для отладки и логирования приложения
 * Вдохновил и описал:
 * https://medium.com/aubergine-solutions/extension-function-to-log-messages-in-kotlin-ed853c247e50
 * Подробнее о функциях расширения здесь: https://antonioleiva.com/extension-functions-kotlin/
 */

//Вывод сообщения в лог отладки и тестирования
fun Activity.logd(message: String) {
    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
}
//Функция для отображения Toast (LENGTH_SHORT) в Activity
fun Activity.shorttoast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}