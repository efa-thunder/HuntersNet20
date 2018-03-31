package com.simbirsoft.itplace.efa.huntersnet.userprofile.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.arellomobile.mvp.presenter.InjectPresenter
import com.simbirsoft.itplace.efa.huntersnet.userprofile.presentation.view.CurrentUserProfileView
import com.simbirsoft.itplace.efa.huntersnet.userprofile.presentation.presenter.CurrentUserProfilePresenter

import com.arellomobile.mvp.MvpAppCompatActivity
import com.simbirsoft.itplace.efa.huntersnet.LoginActivity
import com.simbirsoft.itplace.efa.huntersnet.R


class CurrentUserProfileActivity : MvpAppCompatActivity(), CurrentUserProfileView {

    companion object {
        const val TAG = "CurrentUserProfileActivity"
        fun getIntent(context: Context): Intent = Intent(context, CurrentUserProfileActivity::class.java)
    }

    @InjectPresenter
    lateinit var mCurrentUserProfilePresenter: CurrentUserProfilePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_user_profile)
    }

    override fun gotoLoginScreen() {
        startActivity(Intent(this@CurrentUserProfileActivity,
                LoginActivity::class.java))
    }
}
