package com.example.android.findmybook.presentation.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android.findmybook.R
import com.example.android.findmybook.data.remote.model.BookSearchResponse
import com.example.android.findmybook.data.remote.model.Item
import com.example.android.findmybook.domain.model.Book
import com.example.android.findmybook.presentation.viewmodels.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItem(book: Item) {
    val customCardElevation = CardDefaults.cardElevation(
        defaultElevation = 10.dp
    )
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = Modifier
            .fillMaxWidth(),
        elevation = customCardElevation,
        onClick = { showApp() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.bookicon),
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
                    .fillMaxWidth(0.1f)
                    .padding(end = 5.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
                Text(
                    text = book.volumeInfo.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = book.volumeInfo.authors[0],
                    fontSize = 14.sp
                )
            }
            val imageId = rememberSaveable {
                if(book.saved) mutableStateOf(R.drawable.ic_baseline_bookmark_24)
                else mutableStateOf(R.drawable.ic_baseline_bookmark_border_24)
            }
            Image(
                painter = painterResource(id = imageId.value),
                contentDescription = "",
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false, radius = 16.dp)
                    ) {
                        if (!book.saved) {
                            book.saved = true
                            imageId.value = R.drawable.ic_baseline_bookmark_24
                        } else {
                            book.saved = false
                            imageId.value = R.drawable.ic_baseline_bookmark_border_24
                        }
                    }
            )
        }
    }
}

fun showApp() {

}