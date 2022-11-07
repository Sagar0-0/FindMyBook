package com.example.android.findmybook.model

data class BooksDTO(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)