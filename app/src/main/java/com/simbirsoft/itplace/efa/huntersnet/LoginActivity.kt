package com.simbirsoft.itplace.efa.huntersnet

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.simbirsoft.itplace.efa.huntersnet.utilities.logd
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("Вызван метод OnCreate()...")
        setContentView(R.layout.activity_login)

        register_tv.setOnClickListener {
            startActivity(Intent(
                    this,
                    CreateAccountActivity::class.java
            ))
        }
    }
}
