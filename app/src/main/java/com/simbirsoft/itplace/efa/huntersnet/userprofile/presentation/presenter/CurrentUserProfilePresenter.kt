package com.simbirsoft.itplace.efa.huntersnet.userprofile.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.simbirsoft.itplace.efa.huntersnet.userprofile.presentation.view.CurrentUserProfileView

@InjectViewState
class CurrentUserProfilePresenter : MvpPresenter<CurrentUserProfileView>() {

    // Определим переменные для хранения данных профиля пользователя
    //private var myUserData: com.simbirsoft.itplace.efa.huntersnet.models.User? = null
    private var mDisplayName: String? = null
    private var mFirstName: String? = null
    private var mLastName: String? = null
    private var mStatus: String? = null
    private var mImage: String? = null
    private var mThumbImage: String? = null
    private var mPhone: String? = null

    private var mEmail: String? = null
    private var mUserID: String? = null
    private var mVerified: String? = null

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
        } else onGetCurrentUserData(currentUser)
    }

    private fun onGetCurrentUserData(currentAppUser: FirebaseUser) {
        mDatabaseReference!!.child(currentAppUser.uid)
                .addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot?) {

                        mEmail = currentAppUser.email
                        mUserID = currentAppUser.uid
                        mVerified = currentAppUser.isEmailVerified.toString()

                        mDisplayName = dataSnapshot!!.child("display_name").value as String
                        mFirstName = dataSnapshot.child("first_name").value as String
                        mLastName = dataSnapshot.child("last_name").value as String
                        mStatus = dataSnapshot.child("status").value as String
                        mImage = dataSnapshot.child("image").value as String
                        mThumbImage = dataSnapshot.child("thumb_image").value as String
                        mPhone = dataSnapshot.child("phone_number").value as String

                        viewState.showDisplayName(keyValue = mDisplayName!!)
                        viewState.showFirstName(keyValue = mFirstName!!)
                        viewState.showLastName(keyValue = mLastName!!)
                        viewState.showStatus(keyValue = mStatus!!)
                        viewState.showPhoneNumber(keyValue = mPhone!!)
                        viewState.showEmail(keyValue = mEmail!!)
                        viewState.showUserID(keyValue = mUserID!!)
                        viewState.showVerification(keyValue = mVerified!!)
                    }

                    override fun onCancelled(databaseError: DatabaseError?) {
                        //
                    }
        })
    }

    private fun initializeFirebaseReferences() {
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
    }

    /*fun init() {
        viewState.showDisplayName(keyValue = mDisplayName!!)
        viewState.showFirstName(keyValue = mFirstName!!)
        viewState.showLastName(keyValue = mLastName!!)
        viewState.showStatus(keyValue = mStatus!!)
        viewState.showEmail(keyValue = mEmail!!)
        viewState.showUserID(keyValue = mUserID!!)
        viewState.showVerification(keyValue = mVerified!!)
    }*/

}
