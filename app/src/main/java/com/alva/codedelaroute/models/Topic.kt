package com.alva.codedelaroute.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Topic : RealmObject {
    @PrimaryKey
    var id: String = ""
    var parentId: String = ""
    var status: Int = 0
    var name: String = ""
    var description: String = ""
    var orderIndex: Int = 0
    var totalQuestion: Int = 0
    var allowMistakes: Int = 0
    var numMaster: Int = 0
    var childrenType: Int = 0
    var type: Int = 0
    var lastUpdate: Double = 0.0
    var thumbnail: String = ""
    var icon: String = ""
    var tag: String = ""
}