package com.example.android.findmybook.repository.network

import com.example.android.findmybook.network.model.Item

interface NetworkRepository {
    suspend fun searchBookByTitle(title: String): List<Item>
}