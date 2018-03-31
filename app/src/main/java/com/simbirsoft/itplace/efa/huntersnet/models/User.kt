package com.simbirsoft.itplace.efa.huntersnet.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class User {

    lateinit var displayname: String
    lateinit var firstname: String
    lateinit var lastname: String
    lateinit var status: String
    lateinit var phonenumber: String
    lateinit var image: String
    lateinit var thumbimage: String

    constructor()

    constructor(
            displayname: String?,
            firstname: String?,
            lastname: String?,
            status: String?,
            phonenumber: String?,
            image: String?,
            thumbimage: String?

    ) {
        this.displayname = displayname?: ""
        this.firstname = firstname?: ""
        this.lastname = lastname?: ""
        this.status = status?: ""
        this.phonenumber = phonenumber?: ""
        this.image = image?: ""
        this.thumbimage = thumbimage?: ""

    }

}