package com.simbirsoft.itplace.efa.huntersnet

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.simbirsoft.itplace.efa.huntersnet.utilities.logd

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("Вызван метод OnCreate()...")
        setContentView(R.layout.activity_login)
    }
}
