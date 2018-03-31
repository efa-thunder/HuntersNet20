package com.simbirsoft.itplace.efa.huntersnet

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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

        // Валидация введенных данных
        if (!validateCredentials(this.email!!, this.password!!, this.displayname!!)) {
            return
        }

        mProgressBar!!.setMessage(this.resources
                .getText(R.string.createAccount_progress_register))
        mProgressBar!!.show()

        mAuth!!.createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener { task ->
                    mProgressBar!!.hide()

                    if (task.isSuccessful) {
                        // Получаем uid - UserID пользователя проекта Firebase
                        val userId = mAuth!!.currentUser!!.uid
                        /*
                        Подтверждение регистрации пользователя
                        Высылаем письмо со ссылкой для подтверждения регистрации аккаунта
                        в приложении
                         */
                        verifyRegistration()

                        /*
                          Добавляем дополнительную информацию о профиле пользователя в базу данных
                           в Users:
                             1. display_name - отображаемое имя пользователя (никнейм)
                             2. first_name   - имя
                             3. last_name    - фамилия
                             4. status       - статус пользователя
                             5. phone_number - номер телефона
                             6. image        - фотография профиля
                         */
                        val currentDatabaseUser = mDatabaseReference!!.child(userId)

                        val userObject = hashMapOf<String, String>()
                        userObject["display_name"] = displayname!!
                        userObject["first_name"] = "New"
                        userObject["last_name"] = "Hunter"
                        userObject["status"] = "Registering..."
                        userObject["phone_number"] = "+79990123456"
                        userObject["image"] = "default"
                        userObject["thumb_image"] = "default"

                        currentDatabaseUser!!.setValue(userObject)
                                .addOnCompleteListener { taskDatabase: Task<Void> ->
                                    if (taskDatabase.isSuccessful) {
                                        shorttoast(message = this.resources
                                                .getString(R.string.userProfile_created))
                                    } else {
                                        shorttoast(message = this.resources
                                                .getString(R.string.userProfile_notCreated))
                                    }
                                }
                        // Переходим на страницу профиля пользователя
                        gotoUserProfileFromCreateAccountActivity()
                    } else {

                        when (task.exception) {
                            // Обработка исключений: адрес электронной почты уже используется
                            is FirebaseAuthUserCollisionException -> shorttoast(
                                    message = this.resources.getString(R.string
                                            .fb_auth_exception_usercollision)
                            )
                            // Обработка исключений: адрес электронной почты не корректен
                            is FirebaseAuthInvalidCredentialsException -> shorttoast(
                                    message = this.resources.getString(R.string
                                            .fb_auth_exception_invalidcredentials)
                            )
                            // Обработка исключений: слабый пароль (менее 6 символов)
                            is FirebaseAuthWeakPasswordException -> shorttoast(
                                    message = this.resources.getString(R.string
                                            .fb_auth_exception_weakpassword)
                            )
                            // Обработка исключений: иные невозможные исключения
                            else -> {
                                shorttoast(message = this.resources
                                        .getString(R.string.createNewAccount_failed))
                            }
                        }

                    }
                }
    }

    // Переходи на UserProfile Activity - Профиль пользователя
    private fun gotoUserProfileFromCreateAccountActivity() {
        val intent = Intent (this@CreateAccountActivity,
                UserProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
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
                        shorttoast(message = this.resources
                                .getString(R.string.emailConfirmation_success))
                    } else {
                        shorttoast(message = this.resources
                                .getString(R.string.emailConfirmation_failed))
                    }
                }
    }

    /*
    Валидация введенных данных
     */
    private fun validateCredentials(email: String, password: String, displayname: String): Boolean {

        if (TextUtils.isEmpty(email)) {
            shorttoast(message = this.resources.getString(R.string.validateToast_email_enter))
            return false
        }

        if (TextUtils.isEmpty(password)) {
            shorttoast(message = this.resources.getString(R.string.validateToast_password_enter))
            return false
        }

        if (TextUtils.isEmpty(displayname)) {
            shorttoast(message = this.resources.getString(R.string.validateToast_displayname_enter))
            return false
        }

        if (password.length < 6) {
            shorttoast(message = this.resources.getString(R.string.validateToast_password_short))
            return false
        }

        return true
    }


}
