package com.alva.codedelaroute.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class UserInfo : RealmObject {
    @PrimaryKey
    var id: String = ""
    var email: String = ""
}