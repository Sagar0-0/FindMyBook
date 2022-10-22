package com.example.android.findmybook.model

data class Book(
    val bookName: String,
    private val authorName: String,
    val language: String,
    val image: String,
    val url: String
)