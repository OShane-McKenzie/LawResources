package com.litecodez.lawresources

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun LawItems(law: LawSources) {

    val filteredData by rememberSaveable {
        mutableStateOf(
            law.contents[1].values.toList()
        )
    }


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
        
        itemsIndexed(filteredData){index, item ->
            var subsections = ""
            law.sections["${index+1}"]?.subsection?.keys?.toList()?.forEach {
                subsections += "Sub-section $it: ${law.sections["${index+1}"]?.subsection?.get(it)}\n\n"
            }
            if(searchText=="" || subsections.lowercase(Locale.ROOT).contains(searchText.lowercase(Locale.ROOT)) || item.lowercase(Locale.ROOT).contains(searchText)) {
                ExpandableCard(title = "Section ${index + 1}: $item", description = subsections)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}