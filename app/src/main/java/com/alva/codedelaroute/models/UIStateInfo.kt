package com.alva.codedelaroute.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UIStateInfo(
    var id: String = "",
    var parentId: String = "",
    var name: String = "",
    var shortName: String = "",
    var governmentAgency: String = "",
    var flag: String = ""
) {
    companion object {
        fun fromRealm(stateInfo: StateInfo): UIStateInfo {
            return UIStateInfo(
                id = stateInfo.id,
                parentId = stateInfo.parentId,
                name = stateInfo.name,
                shortName = stateInfo.shortName,
                governmentAgency = stateInfo.governmentAgency,
                flag = stateInfo.flag
            )
        }
    }
}

class StateInfo : RealmObject {
    @PrimaryKey
    var id: String = ""
    var parentId: String = ""
    var name: String = ""
    var shortName: String = ""
    var governmentAgency: String = ""
    var flag: String = ""
}