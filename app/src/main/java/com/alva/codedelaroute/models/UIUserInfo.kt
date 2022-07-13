package com.alva.codedelaroute.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UIUserInfo(
    var id: String = "",
    var email: String = "",
) {
    companion object {
        fun fromRealm(userInfo: UserInfo): UIUserInfo {
            return UIUserInfo(
                id = userInfo.id,
                email = userInfo.email
            )
        }
    }
}

class UserInfo : RealmObject {
    @PrimaryKey
    var id: String = ""
    var email: String = ""
}