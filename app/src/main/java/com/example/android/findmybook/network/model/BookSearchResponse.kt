package com.example.android.findmybook.network.model

data class BookSearchResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<BookDTO>
)
