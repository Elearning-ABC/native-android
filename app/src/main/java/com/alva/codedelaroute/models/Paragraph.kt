package com.alva.codedelaroute.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Paragraph : RealmObject {
    @PrimaryKey
    var id: String = ""
    var content: String = ""
    var lastUpdate: Double = 0.0
}