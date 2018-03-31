package com.simbirsoft.itplace.efa.huntersnet.userprofile.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface CurrentUserProfileView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun gotoLoginScreen()
}
