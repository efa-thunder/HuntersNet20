package com.simbirsoft.itplace.efa.huntersnet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.simbirsoft.itplace.efa.huntersnet.utilities.logd

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("Вызван метод OnCreate()...")
        setContentView(R.layout.activity_user_profile)
    }
}
