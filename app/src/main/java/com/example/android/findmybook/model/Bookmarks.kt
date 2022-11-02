package com.example.android.findmybook.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class Bookmarks(
    val set: Set<String> = setOf()
)