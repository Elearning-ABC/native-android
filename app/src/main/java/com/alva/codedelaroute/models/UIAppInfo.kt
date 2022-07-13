package com.alva.codedelaroute.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UIAppInfo(
    var id: String = "",
    var name: String = "",
    var title: String = "",
    var description: String = "",
    var buildNumber: String = "",
    var packageName: String = "",
    var bucket: String = "",
    var website: String = "",
    var urlAndroid: String = "",
    var urlIos: String = "",
    var thumbnail: String = "",
    var version: String = "",
    var hasState: Boolean = false,
    var lastUpdate: Double = 0.0
) {
    companion object {
        fun fromRealm(appInfo: AppInfo): UIAppInfo {
            return UIAppInfo(
                id = appInfo.id,
                name = appInfo.name,
                title = appInfo.title,
                description = appInfo.description,
                buildNumber = appInfo.buildNumber,
                packageName = appInfo.packageName,
                bucket = appInfo.bucket,
                website = appInfo.website,
                urlAndroid = appInfo.urlAndroid,
                urlIos = appInfo.urlIos,
                thumbnail = appInfo.thumbnail,
                version = appInfo.version,
                hasState = appInfo.hasState,
                lastUpdate = appInfo.lastUpdate
            )
        }
    }
}

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