package com.simbirsoft.itplace.efa.huntersnet.userprofile.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.simbirsoft.itplace.efa.huntersnet.userprofile.presentation.view.CurrentUserProfileView

@InjectViewState
class CurrentUserProfilePresenter : MvpPresenter<CurrentUserProfileView>() {

    // Определим ссылки на Firebase
    private var mAuth: FirebaseAuth? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        initializeFirebaseReferences()

        val currentUser = mAuth!!.currentUser

        if (currentUser == null) {
            viewState.gotoLoginScreen()
        } else onGetCurrentUserData(currentUser = currentUser)
    }

    private fun onGetCurrentUserData(currentUser: FirebaseUser) {
        val currentUserReference = mDatabaseReference!!.child(currentUser.uid)
                .addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(p0: DataSnapshot?) {
                        //
                    }

                    override fun onCancelled(p0: DatabaseError?) {
                        //
                    }
                })
    }

    private fun initializeFirebaseReferences() {
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
    }

}
