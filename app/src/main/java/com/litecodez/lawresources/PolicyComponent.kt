package com.litecodez.lawresources

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A composable function that creates a dropdown menu with the given items.
 *
 * @param onClicked A function to be called when an item is selected.
 * @param setItems A list of items to display in the dropdown menu.
 * @param placeholderText The text to display as a placeholder when no item is selected.
 */
inline fun <T> LazyListScope.lazyItems(
    items: List<T>,
    noinline key: ((item: T) -> Any)? = null,
    noinline contentType: (item: T) -> Any? = { null },
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit
) = items(
    count = items.size,
    key = if (key != null) { index: Int -> key(items[index]) } else null,
    contentType = { index: Int -> contentType(items[index]) }
) {
    itemContent(items[it])
}

/**
 * A composable function that displays the contents of a PolicyData object as expandable cards.
 *
 * @param policyData The PolicyData object to display.
 */
@Composable
fun PolicyDataItems(policyData: Policy) {

    val filteredData = mutableStateOf(
        policyData.contents.keys.toList()
    )

    var searchText by rememberSaveable { mutableStateOf("") }
    Spacer(modifier = Modifier.height(4.dp))
    OutlinedTextField(
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color(0xFFA05A2C),
            unfocusedIndicatorColor = Color(0xFFE1BC9A),
            backgroundColor = Color(0xFFF5F5F5)
        ),
        value = searchText,
        onValueChange = { newText -> searchText = newText },
        placeholder = { Text(text = PlainStrings.CENTERED_PLACE_HOLDER.getString,
            textAlign = TextAlign.Center, color = Color.Black) },
        textStyle = TextStyle(
            textAlign = TextAlign.Center
        )

    )
    Spacer(modifier = Modifier.height(4.dp))
    LazyColumn(
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(filteredData.value){index,key->
            //policyData.sections[index][key]?.get(0) ?: ""
            var sections = ""
            policyData.sections[index][key]?.forEachIndexed() {index2,item->
                sections+=item
                if(index2!=policyData.sections[index][key]?.lastIndex){
                    sections+="\n\n"
                }
            }
            if(searchText=="" || key.contains(searchText, ignoreCase = true) ||
                sections.contains(searchText, ignoreCase = true)) {
                ExpandableCard(
                    title = key,
                    description = sections,
                    titleFontSize = MaterialTheme.typography.subtitle2.fontSize,
                    titleFontWeight = FontWeight.Bold,
                    descriptionFontSize = MaterialTheme.typography.body2.fontSize,
                    descriptionFontWeight = FontWeight.Normal,
                    descriptionMaxLines = Int.MAX_VALUE,
                    elevation = 2.dp
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
