package com.example.android.findmybook.network.model

import com.example.android.findmybook.domain.model.Book
import com.example.android.findmybook.domain.util.DomainMapper

class BookDtoMapper:DomainMapper<BookDTO,Book> {
    override fun mapToDomainModel(entity: BookDTO): Book {
        return Book(
            id=entity.id,
            selfLink = entity.selfLink,
            title=entity.volumeInfo.title,
            authors=entity.volumeInfo.authors,
            publisher=entity.volumeInfo.publisher,
            publishedDate=entity.volumeInfo.publishedDate,
            description=entity.volumeInfo.description,
            pageCount=entity.volumeInfo.pageCount,
            categories=entity.volumeInfo.categories,
            imageLink=entity.volumeInfo.imageLinks.thumbnail,
            language=entity.volumeInfo.language,
            previewLink=entity.volumeInfo.previewLink
        )
    }

    override fun mapFromDomainModel(domainModel: Book): BookDTO {
        TODO("Not needed")
    }

    fun toDomainList(initial: List<BookDTO>): List<Book> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Book>): List<BookDTO> {
        return initial.map { mapFromDomainModel(it) }
    }
}