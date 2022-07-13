package com.alva.codedelaroute.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UIParagraph(
    var id: String = "",
    var content: String = "",
    var lastUpdate: Double = 0.0
) {
    companion object {
        fun fromRealm(paragraph: Paragraph): UIParagraph {
            return UIParagraph(
                id = paragraph.id,
                content = paragraph.content,
                lastUpdate = paragraph.lastUpdate
            )
        }
    }
}

open class Paragraph : RealmObject {
    @PrimaryKey
    var id: String = ""
    var content: String = ""
    var lastUpdate: Double = 0.0
}