package com.example.android.findmybook.data.remote.model

data class BookSearchResponse(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)