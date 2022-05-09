package com.alva.codedelaroute.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class StateInfo : RealmObject {
    @PrimaryKey
    var id: String = ""
    var parentId: String = ""
    var name: String = ""
    var shortName: String = ""
    var governmentAgency: String = ""
    var flag: String = ""
}