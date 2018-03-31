package com.simbirsoft.itplace.efa.huntersnet.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class User {
    var displayname: String? = null
    var firstname: String? = null
    var lastname: String? = null
    var status: String? = null
    var phonenumber: String? = null
    var image: String? = null
    var thumbimage: String? = null

    constructor() {
        // Default Constructor required for calls to DataSnapshot.getValue(User.class)
    }

    constructor(displayname: String?, firstname: String?, lastname: String?, status: String?,
                phonenumber: String?, image: String?, thumbimage: String?) {
        this.displayname = displayname
        this.firstname = firstname
        this.lastname = lastname
        this.status = status
        this.phonenumber = phonenumber
        this.image = image
        this.thumbimage = thumbimage
    }

}