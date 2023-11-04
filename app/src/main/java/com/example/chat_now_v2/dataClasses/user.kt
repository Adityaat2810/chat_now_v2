package com.example.chat_now_v2.dataClasses

import android.net.Uri


class user {
    var name: String? = null
    var email: String? = null
    var uid: String? = null
    var imageUri: String? = null
    var phoneNumber: String? = null
    var age: String? = null
    var gender:String?=null

    constructor() {}

    constructor(name: String?, email: String?, uid: String?, imageUri: String?, phoneNumber: String?, age: String?,gender:String?) {
        this.name = name
        this.email = email
        this.uid = uid
        this.imageUri = imageUri
        this.phoneNumber = phoneNumber
        this.age = age
        this.gender=gender
    }
}
