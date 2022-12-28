package com.example.android.findmybook.presentation

sealed class BookSearchEvent {
    object NewSearch :BookSearchEvent()
    object NextPageSearch :BookSearchEvent()
    object RestoreSearchData : BookSearchEvent()
}
