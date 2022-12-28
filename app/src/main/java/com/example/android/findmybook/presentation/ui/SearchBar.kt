package com.example.android.findmybook.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.android.findmybook.presentation.BookSearchEvent
import com.example.android.findmybook.presentation.BookSearchEvent.NewSearch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    isInvalidInput: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager,
    onTriggerEvent: (BookSearchEvent) -> Unit,
    validateQuery: () -> Unit
) {
    OutlinedTextField(
        value = query,
        isError = isInvalidInput,
        supportingText = {
            if (isInvalidInput) {
                Text(text = errorMessage)
            }
        },
        singleLine = true,
        maxLines = 1,
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                end = 10.dp
            ),
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text("Search")
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false, radius = 18.dp)
                ) {
                    focusManager.clearFocus()
                    query.trim()
                    validateQuery()
                    onValueChange(query)
                    onTriggerEvent(NewSearch)
                }
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(onSearch = {
            focusManager.clearFocus()
            query.trim()
            onValueChange(query)
            onTriggerEvent(NewSearch)
        }),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface)
    )
}