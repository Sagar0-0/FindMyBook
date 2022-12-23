package com.example.android.findmybook.domain.model

data class Book(
    val id: String,
    val selfLink: String,
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val pageCount: Int,
    val categories: List<String>,
    val imageLink: String,
    val language: String,
    val previewLink: String
)
