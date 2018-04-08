package com.simbirsoft.itplace.efa.huntersnet.userprofile.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.arellomobile.mvp.presenter.InjectPresenter
import com.simbirsoft.itplace.efa.huntersnet.userprofile.presentation.view.CurrentUserProfileView
import com.simbirsoft.itplace.efa.huntersnet.userprofile.presentation.presenter.CurrentUserProfilePresenter

import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.PresenterType
import com.simbirsoft.itplace.efa.huntersnet.LoginActivity
import com.simbirsoft.itplace.efa.huntersnet.R
import kotlinx.android.synthetic.main.activity_current_user_profile.*


class CurrentUserProfileActivity : MvpAppCompatActivity(), CurrentUserProfileView {

    companion object {
        const val TAG = "CurrentUserProfileActivity"
        fun getIntent(context: Context): Intent = Intent(context, CurrentUserProfileActivity::class.java)
    }

    private var tvDisplayName: TextView? = null
    private var tvFirstName: TextView? = null
    private var tvLastName: TextView? = null
    private var tvStatus: TextView? = null
    private var tvPhoneNumber: TextView? = null
    private var tvEmail: TextView? = null
    private var tvUserId: TextView? = null
    private var tvVerified: TextView? = null

    @InjectPresenter(type = PresenterType.LOCAL)
    lateinit var mCurrentUserProfilePresenter: CurrentUserProfilePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_user_profile)

        initializeUI()
        // mCurrentUserProfilePresenter.init()
        edit_profile_tv.setOnClickListener {
            goToEditProfile()
        }

    }

    private fun goToEditProfile() {
        val intent = Intent (this@CurrentUserProfileActivity,
                EditCurrentUserProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun initializeUI() {
        tvDisplayName = findViewById<View>(R.id.display_name_profile_tv) as TextView
        tvFirstName = findViewById<View>(R.id.firstname_tv) as TextView
        tvLastName = findViewById<View>(R.id.lastname_tv) as TextView
        tvStatus = findViewById<View>(R.id.status_profile_tv) as TextView
        tvPhoneNumber = findViewById<View>(R.id.profile_phone_tv) as TextView
        tvEmail = findViewById<View>(R.id.profile_email_tv) as TextView
        tvUserId = findViewById<View>(R.id.uid_tv) as TextView
        tvVerified = findViewById<View>(R.id.verified_tv) as TextView
    }

    override fun gotoLoginScreen() {
        startActivity(Intent(this@CurrentUserProfileActivity,
                LoginActivity::class.java))
    }

    override fun showDisplayName(keyValue: String) {
        tvDisplayName?.text = keyValue
    }

    override fun showStatus(keyValue: String) {
        tvStatus?.text = keyValue
    }

    override fun showFirstName(keyValue: String) {
        tvFirstName?.text = keyValue
    }

    override fun showLastName(keyValue: String) {
        tvLastName?.text = keyValue
    }

    override fun showEmail(keyValue: String) {
        tvEmail?.text = keyValue
    }

    override fun showPhoneNumber(keyValue: String) {
        tvPhoneNumber?.text = keyValue
    }

    override fun showUserID(keyValue: String) {
        tvUserId?.text = keyValue
    }

    override fun showVerification(keyValue: String) {
        tvVerified?.text = keyValue
    }

}
