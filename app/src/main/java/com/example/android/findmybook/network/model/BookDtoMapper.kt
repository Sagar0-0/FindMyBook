package com.example.android.findmybook.network.model

import com.example.android.findmybook.domain.model.Book
import com.example.android.findmybook.domain.util.DomainMapper

class BookDtoMapper:DomainMapper<BookDTO,Book> {
    override fun mapToDomainModel(bookDTO: BookDTO): Book {
        return Book(
            id=bookDTO.id,
            selfLink = bookDTO.selfLink,
            title=bookDTO.volumeInfo.title,
            authors=bookDTO.volumeInfo.authors,
            publisher=bookDTO.volumeInfo.publisher,
            publishedDate=bookDTO.volumeInfo.publishedDate,
            description=bookDTO.volumeInfo.description,
            pageCount=bookDTO.volumeInfo.pageCount,
            categories=bookDTO.volumeInfo.categories,
            imageLink=bookDTO.volumeInfo.imageLinks.thumbnail,
            language=bookDTO.volumeInfo.language,
            previewLink=bookDTO.volumeInfo.previewLink
        )
    }

    override fun mapFromDomainModel(domainModel: Book): BookDTO {
        TODO("Not yet implemented")
    }

    fun toDomainList(initial: List<BookDTO>): List<Book> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Book>): List<BookDTO> {
        return initial.map { mapFromDomainModel(it) }
    }
}