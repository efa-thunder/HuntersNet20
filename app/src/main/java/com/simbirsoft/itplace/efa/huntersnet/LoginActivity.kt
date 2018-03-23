package com.simbirsoft.itplace.efa.huntersnet

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.simbirsoft.itplace.efa.huntersnet.utilities.logd
import com.simbirsoft.itplace.efa.huntersnet.utilities.shorttoast
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

        // Валидация введенных данных
        if (!validateCredentials(this.email!!, this.password!!)) {
            return
        }

        mProgressBar!!.setMessage(this.resources.getText(R.string.login_progress))
        mProgressBar!!.show()

        mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->

                    mProgressBar!!.hide()

                    if (task.isSuccessful) {
                        //успех
                        gotoUserProfile()
                    } else {
                        //провал
                        /*
                        Exceptions:
                        1. FirebaseAuthInvalidUserException thrown if the user account
                        corresponding to email does not exist or has been disabled
                        2. FirebaseAuthInvalidCredentialsException thrown if the password is wrong
                         */
                    }
                }
    }

    /*
    Запускаем UserProfileActivity с флагом FLAG_ACTIVITY_CLEAR_TOP
      (FLAG_ACTIVITY_CLEAR_TOP - изучить более подробно флаги)
     */
    private fun gotoUserProfile() {
        val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    /*
      Валидация введенных данных
    */
    private fun validateCredentials(email: String, password: String): Boolean {

        if (TextUtils.isEmpty(email)) {
            shorttoast(message = this.resources.getString(R.string.validateToast_email_enter))
            return false
        }

        if (TextUtils.isEmpty(password)) {
            shorttoast(message = this.resources.getString(R.string.validateToast_password_enter))
            return false
        }

        if (password.length < 6) {
            shorttoast(message = this.resources.getString(R.string.validateToast_password_short))
            return false
        }

        return true
    }

}
