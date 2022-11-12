package com.example.android.findmybook.repository.network

import com.example.android.findmybook.domain.model.Book
import com.example.android.findmybook.network.NetworkService
import com.example.android.findmybook.network.model.BookDtoMapper
import javax.inject.Inject

class NetworkRepo_Impl(
    @Inject
    private val networkService: NetworkService,
    private val mapper: BookDtoMapper
) : NetworkRepository {

    override suspend fun searchBookByTitle(title: String): List<Book> {
        return mapper.toDomainList(
            networkService.searchBookByTitle(title).books
        )
    }

}