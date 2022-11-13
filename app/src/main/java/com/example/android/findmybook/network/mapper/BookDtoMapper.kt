package com.example.android.findmybook.network.mapper

import com.example.android.findmybook.domain.model.Book
import com.example.android.findmybook.domain.util.DomainMapper
import com.example.android.findmybook.network.model.Item

class BookDtoMapper:DomainMapper<Item,Book> {
    override fun mapToDomainModel(entity: Item): Book {
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

    override fun mapFromDomainModel(domainModel: Book): Item {
        TODO("Not needed")
    }

    fun toDomainList(initial: List<Item>): List<Book> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Book>): List<Item> {
        return initial.map { mapFromDomainModel(it) }
    }
}