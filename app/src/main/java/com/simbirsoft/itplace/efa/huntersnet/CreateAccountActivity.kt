package com.simbirsoft.itplace.efa.huntersnet

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.widget.Button
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.simbirsoft.itplace.efa.huntersnet.utilities.logd

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
    // private var mDatabase: FirebaseDatabase? = null
    // private var mDatabaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("Вызван метод OnCreate()...")
        setContentView(R.layout.activity_create_account)
    }
}
