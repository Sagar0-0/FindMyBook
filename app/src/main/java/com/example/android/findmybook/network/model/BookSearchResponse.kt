package com.example.android.findmybook.network.model

data class BookSearchResponse(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)