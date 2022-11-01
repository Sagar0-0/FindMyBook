package com.example.android.findmybook

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import com.example.android.findmybook.datastore.BookmarksSerializer
import com.example.android.findmybook.model.Bookmarks
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.launch


val Context.datastore: DataStore<Bookmarks> by dataStore("bookmarks.json", BookmarksSerializer)

class MainActivity : ComponentActivity() {
    var count =1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Row {
                val coroutineScope = rememberCoroutineScope()
                val data = remember {
                    mutableStateOf("SAGAR")
                }
                Button(onClick = {
                    coroutineScope.launch {
                        addBookmark()
                        datastore.data.collect {
                            data.value=it.set.toString()
                        }
                    }
                }) {
                    Text(text = "Button")
                }

                Row {
                    Text(text = data.value)
                }
            }
        }
    }

    private suspend fun addBookmark() {
        datastore.updateData {
            it.copy(
                set = it.set.plusElement(count.toString())
            )
        }
        count++
    }
}