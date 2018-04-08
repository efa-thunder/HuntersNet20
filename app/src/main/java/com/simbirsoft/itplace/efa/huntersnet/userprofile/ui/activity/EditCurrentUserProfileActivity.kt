package com.simbirsoft.itplace.efa.huntersnet.userprofile.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.simbirsoft.itplace.efa.huntersnet.R
import com.simbirsoft.itplace.efa.huntersnet.userprofile.presentation.presenter.EditCurrentUserProfilePresenter
import com.simbirsoft.itplace.efa.huntersnet.userprofile.presentation.view.EditCurrentUserProfileView

class EditCurrentUserProfileActivity : MvpAppCompatActivity(), EditCurrentUserProfileView {

    companion object {
        const val TAG = "EditCurrentUserProfileActivity"
        fun getIntent(context: Context): Intent = Intent(context,
                EditCurrentUserProfileActivity::class.java)
    }

    private var tbEditProfile: Toolbar? = null

    @InjectPresenter
    lateinit var mEditCurrentUserProfilePresenter: EditCurrentUserProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_current_user_profile)

        initializeToolbar()
    }

    private fun initializeToolbar() {
        tbEditProfile = findViewById(R.id.editprofile_toolbar)
        setSupportActionBar(tbEditProfile)
        supportActionBar!!.title = this.resources.getString(R.string.editprofile_toolbar_text)
    }
}
