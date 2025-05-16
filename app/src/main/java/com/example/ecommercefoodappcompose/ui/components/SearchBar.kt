package com.example.ecommercefoodappcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.ecommercefoodappcompose.R
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: String,
    onQueryChanged: (String) -> Unit,
    hasSearched: MutableState<Boolean>,
    viewModel: FoodViewModel,
    modifier: Modifier
) {
    val isSearching by viewModel.isSearching.observeAsState(false)
    var debounceJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(Unit) {
        hasSearched.value = false
    }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { newText ->
            onQueryChanged(newText)
            hasSearched.value = newText.isNotBlank()
            debounceJob?.cancel()
            debounceJob = CoroutineScope(Dispatchers.Main).launch {
                delay(500)
                viewModel.searchFoodItems(newText)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        shape = RoundedCornerShape(24.dp),
        trailingIcon = {
            if (isSearching) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                IconButton(onClick = { viewModel.searchFoodItems(searchQuery) }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }
        },
        placeholder = { Text(stringResource(R.string.search_bar_txt)) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions.Default,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.Black
        ),
        singleLine = true
    )
}
