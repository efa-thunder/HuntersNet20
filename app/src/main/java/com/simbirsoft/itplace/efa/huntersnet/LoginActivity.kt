package com.simbirsoft.itplace.efa.huntersnet

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.simbirsoft.itplace.efa.huntersnet.utilities.logd
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    //Определим переменные для UI-элементов
    private var tilEmail: TextInputLayout? = null
    private var etEmail: TextInputEditText? = null
    private var tilPassword: TextInputLayout? = null
    private var etPassword: TextInputEditText? = null
    private var tvRegister: TextView? = null
    private var tvForgotPassword: TextView? = null
    private var btnLogin: Button? = null
    private var ivArrowBack: ImageView? = null
    private var mProgressBar: ProgressDialog? = null

    //Определим ссылки на Firebase Authorization и Database
    private var mAuth: FirebaseAuth? = null

    //Определим глобальные переменные
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("Вызван метод OnCreate()...")
        setContentView(R.layout.activity_login)

        initializeReferences()

        register_tv.setOnClickListener {
            startActivity(Intent(
                    this,
                    CreateAccountActivity::class.java
            ))
        }
    }

    private fun initializeReferences() {
        tilEmail = findViewById<View>(R.id.email_textInputLayout) as TextInputLayout
        etEmail = findViewById<View>(R.id.email_textInput_et) as TextInputEditText
        tilPassword = findViewById<View>(R.id.password_textInputLayout) as TextInputLayout
        etPassword = findViewById<View>(R.id.password_textInput_et) as TextInputEditText
        tvRegister = findViewById<View>(R.id.register_tv) as TextView
        tvForgotPassword = findViewById<View>(R.id.forgot_password_tv) as TextView
        btnLogin = findViewById<View>(R.id.login_btn) as Button
        ivArrowBack = findViewById<View>(R.id.arrow_back_iv) as ImageView
        mProgressBar = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()

        tvRegister!!
                .setOnClickListener {
                    startActivity(Intent(this@LoginActivity,
                            CreateAccountActivity::class.java))
        }

        tvForgotPassword!!
                .setOnClickListener {
                    startActivity(Intent(this@LoginActivity,
                            ForgotPasswordActivity::class.java))
                }

        btnLogin!!.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        email = etEmail?.text.toString().trim()
        password = etPassword?.text.toString().trim()



    }
}
