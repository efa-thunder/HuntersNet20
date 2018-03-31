package com.simbirsoft.itplace.efa.huntersnet.userprofile.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface CurrentUserProfileView : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun gotoLoginScreen()

    fun showDisplayName(keyValue: String)
    fun showStatus(keyValue: String)
    fun showFirstName(keyValue: String)
    fun showLastName(keyValue: String)
    fun showEmail(keyValue: String)
    fun showPhoneNumber(keyValue: String)
    fun showUserID(keyValue: String)
    fun showVerification(keyValue: String)

}
