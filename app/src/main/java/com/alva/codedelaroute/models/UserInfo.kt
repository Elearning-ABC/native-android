package com.alva.codedelaroute.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class UserInfo : RealmObject {
    @PrimaryKey
    var id: String = ""
    var email: String = ""
}