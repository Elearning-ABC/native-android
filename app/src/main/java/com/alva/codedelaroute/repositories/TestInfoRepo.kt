package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.TestInfo
import com.alva.codedelaroute.models.UITestInfo
import io.realm.kotlin.Realm

class TestInfoRepo(private val realm: Realm) {
    fun getAllTestInfo(): MutableList<UITestInfo> {
        val results = realm.query(TestInfo::class).find()
        return results.map { UITestInfo.fromRealm(it) }.sortedBy { it.index }.toMutableList()
    }
}