package com.simbirsoft.itplace.efa.huntersnet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.simbirsoft.itplace.efa.huntersnet.utilities.logd

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("Вызван метод OnCreate()...")
        setContentView(R.layout.activity_forgot_password)
    }
}
