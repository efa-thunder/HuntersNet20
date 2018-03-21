package com.simbirsoft.itplace.efa.huntersnet

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.simbirsoft.itplace.efa.huntersnet.utilities.logd
import com.simbirsoft.itplace.efa.huntersnet.utilities.shorttoast

class CreateAccountActivity : AppCompatActivity() {

    //Определим переменные для UI-элементов
    private var etEmail: TextInputEditText? = null
    private var etPassword: TextInputEditText? = null
    private var etDisplayName: TextInputEditText? = null
    private var btnCreateAccount: Button? = null
    private var ivArrowBack: ImageView? = null
    private var mProgressBar: ProgressDialog? = null

    //Определим ссылки на Firebase Authorization и Database
    private var mAuth: FirebaseAuth? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null

    //Определим глобальные переменные
    private var email: String? = null
    private var password: String? = null
    private var displayname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        logd("Вызван метод OnCreate()...")

        initializeReferences()
    }

    /*
    Инициализация взаимосвязей в Activity - UI, Firebase Auth&Database
     */
    private fun initializeReferences() {
        etEmail = findViewById<View>(R.id.ca_email_textInput_et) as TextInputEditText
        etPassword = findViewById<View>(R.id.ca_password_textInput_et) as TextInputEditText
        etDisplayName = findViewById<View>(R.id.ca_displayname_textInput_et) as TextInputEditText
        btnCreateAccount = findViewById<View>(R.id.create_account_btn) as Button
        ivArrowBack = findViewById<View>(R.id.ca_arrow_back_iv) as ImageView
        mProgressBar = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")

        btnCreateAccount!!.setOnClickListener { createNewAccount() }
    }

    private fun createNewAccount() {
        email = etEmail?.text.toString().trim()
        password = etPassword?.text.toString().trim()
        displayname = etDisplayName?.text.toString().trim()

        if (!validateCredentials(email!!, password!!, displayname!!)) {
            return
        }

        mProgressBar!!.setMessage(R.string.createAccount_progress_register.toString())
        mProgressBar!!.show()

        mAuth!!
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    mProgressBar!!.hide()

                    /*
                     Если регистрация прошла успешно, высылаем письмо для подтверждения регистрации
                     и переходим на страницу профиля пользователя
                      */
                    if (task.isSuccessful) {
                        val userId = mAuth!!.currentUser!!.uid
                        /*
                        Подтверждение регистрации пользователя
                        Высылаем письмо со ссылкой для подтверждения регистрации аккаунта
                        в приложении
                         */
                        verifyRegistration()

                        /*
                        Добавляем дополнительную информацию о профиле пользователя в базу данных
                         в Users
                         */
                        val currentDatabaseUser = mDatabaseReference!!.child(userId)

                        val userObject = hashMapOf<String, String>()
                        userObject.put("display_name", displayname!!)
                        userObject.put("first_name", "Mick")
                        userObject.put("last_name", "Dundee")
                        userObject.put("status", "I'll be back...")
                        userObject.put("phone_number", "+79510999999")
                        userObject.put("image", "default")
                        userObject.put("is_visible", "true")

                        currentDatabaseUser!!.setValue(userObject).addOnCompleteListener {
                            taskDatabase: Task<Void> ->
                                if (taskDatabase.isSuccessful) {
                                    shorttoast(message = R.string.userProfile_created.toString())
                                } else {
                                    shorttoast(message = R.string.userProfile_notCreated.toString())
                                }
                        }
                        } else {
                            // Ошибка аутентификации
                            logd("createUserWithEmailAndPassword: failed" + task.exception)
                            shorttoast(message = R.string.createNewAccount_failed.toString())
                            /*
                            ПРОДУМАТЬ!
                            Обработка exceptions:
                            1. FirebaseAuthWeakPasswordException если пароль недостаточно сильный
                            2. FirebaseAuthInvalidCredentialsException если адрес электронной почты
                            неверен
                            3. FirebaseAuthUserCollisionException если уже существует учетная запись
                            с указанным адресом электронной почты
                             */
                    }

                    }

    }

    /*
    Подтверждение регистрации пользователя
    Высылаем письмо со ссылкой для подтверждения регистрации аккаунта
    в приложении
    */
    private fun verifyRegistration() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        logd("verifyRegistration: success")
                        shorttoast(message = R.string.emailConfirmation_success.toString())
                    } else {
                        logd("verifyRegistration: failed " + task.exception)
                        shorttoast(message = R.string.emailConfirmation_failed.toString())
                    }
                }
    }

    /*
    Валидация введенных данных
     */
    private fun validateCredentials(email: String, password: String, displayname: String): Boolean {

        if (TextUtils.isEmpty(email)) {
            shorttoast(message = R.string.validateToast_email_enter.toString())
            return false
        }

        if (TextUtils.isEmpty(password)) {
            shorttoast(message = R.string.validateToast_password_enter.toString())
            return false
        }

        if (TextUtils.isEmpty(displayname)) {
            shorttoast(message = R.string.validateToast_displayname_enter.toString())
            return false
        }

        if (password.length < 6) {
            shorttoast(message = R.string.validateToast_password_short.toString())
            return false
        }

        return true
    }


}
