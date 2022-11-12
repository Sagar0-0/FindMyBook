package com.example.android.findmybook.network.response

import com.example.android.findmybook.network.model.BookDTO
import com.google.gson.annotations.SerializedName

data class BookSearchResponse(

    @SerializedName("totalItems")
    var count:Int,

    @SerializedName("items")
    var books:List<BookDTO>
)
