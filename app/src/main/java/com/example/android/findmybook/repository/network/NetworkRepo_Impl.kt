package com.example.android.findmybook.repository.network

import com.example.android.findmybook.network.NetworkService
import com.example.android.findmybook.network.mapper.BookDtoMapper
import com.example.android.findmybook.network.model.Item
import javax.inject.Inject

class NetworkRepo_Impl @Inject constructor(
    private val api: NetworkService,
    private val mapper: BookDtoMapper
) : NetworkRepository {

    override suspend fun searchBookByTitle(title: String): List<Item> {
        return if (api.searchBookByTitle(title).isSuccessful
            && api.searchBookByTitle(title).body() != null
            && api.searchBookByTitle(title).body()!!.totalItems>0) {
            api.searchBookByTitle(title).body()!!.items
        } else {
            listOf()
        }
    }

}