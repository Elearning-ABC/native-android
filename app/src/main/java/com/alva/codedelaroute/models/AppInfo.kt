package com.alva.codedelaroute.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class AppInfo : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var title: String = ""
    var description: String = ""
    var buildNumber: String = ""
    var packageName: String = ""
    var bucket: String = ""
    var website: String = ""
    var urlAndroid: String = ""
    var urlIos: String = ""
    var thumbnail: String = ""
    var version: String = ""
    var hasState: Boolean = false
    var lastUpdate: Double = 0.0
}