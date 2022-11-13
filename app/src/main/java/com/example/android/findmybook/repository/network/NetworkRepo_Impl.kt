package com.example.android.findmybook.repository.network

import com.example.android.findmybook.domain.model.Book
import com.example.android.findmybook.network.NetworkService
import com.example.android.findmybook.network.mapper.BookDtoMapper
import javax.inject.Inject

class NetworkRepo_Impl @Inject constructor(
    private val networkService: NetworkService,
    private val mapper: BookDtoMapper
) : NetworkRepository {

    override suspend fun searchBookByTitle(title: String): List<Book> {
        return if (networkService.searchBookByTitle(title).isSuccessful && networkService.searchBookByTitle(title).body() != null) {
            mapper.toDomainList(networkService.searchBookByTitle(title).body()!!.items)
        } else {
            arrayListOf()
        }
    }

}