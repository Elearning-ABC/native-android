package com.alva.codedelaroute.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class StateInfo : RealmObject {
    @PrimaryKey
    var id: String = ""
    var parentId: String = ""
    var name: String = ""
    var shortName: String = ""
    var governmentAgency: String = ""
    var flag: String = ""
}