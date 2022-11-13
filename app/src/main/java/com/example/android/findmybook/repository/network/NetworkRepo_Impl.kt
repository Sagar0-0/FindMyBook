package com.example.android.findmybook.repository.network

import android.util.Log
import com.example.android.findmybook.domain.model.Book
import com.example.android.findmybook.network.NetworkService
import com.example.android.findmybook.network.model.BookDTO
import com.example.android.findmybook.network.model.BookDtoMapper
import javax.inject.Inject

class NetworkRepo_Impl @Inject constructor(
    private val networkService: NetworkService,
    private val mapper: BookDtoMapper
) : NetworkRepository {

    override suspend fun searchBookByTitle(title: String): List<Book> {
        Log.d("Hit call", "searchBookByTitle: ${networkService.searchBookByTitle(title).isSuccessful}")
        return mapper.toDomainList(
            if(networkService.searchBookByTitle(title).isSuccessful && networkService.searchBookByTitle(title).body()!=null){
                networkService.searchBookByTitle(title).body()!!.items
            }else{
                arrayListOf()
            }
        )
    }

}