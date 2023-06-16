package com.erkindilekci.newssphere.presentation.searchscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.erkindilekci.newssphere.R
import com.erkindilekci.newssphere.presentation.util.ErrorScreen
import com.erkindilekci.newssphere.presentation.util.LoadingScreen
import com.erkindilekci.newssphere.util.Resource
import com.erkindilekci.newssphere.util.Screen
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val response = viewModel.searchedNews.collectAsState().value

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "TextField" },
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .alpha(alpha = 0.6f),
                        text = "Search..."
                    )
                },
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        modifier = Modifier
                            .alpha(alpha = 0.6f),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search...",
                            tint = Color.Black
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        modifier = Modifier.semantics { contentDescription = "CloseIcon" },
                        onClick = {
                            if (searchQuery.trim().isNotEmpty()) {
                                searchQuery = ""
                            } else {
                                navController.popBackStack()
                                navController.navigate(Screen.ListScreen.route)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.Black
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.searchNews(searchQuery)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
    ) {
        when (response) {
            is Resource.Error -> {
                ErrorScreen("Error")
            }

            is Resource.Loading -> {
                if (response.data?.articles != null) {
                    LoadingScreen()
                }
            }

            is Resource.Success -> {
                Column {
                    val articles = response.data?.articles ?: emptyList()

                    LazyColumn(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        items(articles) { new ->
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate("${Screen.DetailScreen.route}/${new.title}")
                                    },
                            ) {
                                Column {
                                    AsyncImage(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(16f / 9f),
                                        model = new.urlToImage,
                                        contentDescription = null,
                                        placeholder = painterResource(id = R.drawable.placeholder),
                                        error = painterResource(id = R.drawable.placeholder),
                                        contentScale = ContentScale.FillWidth
                                    )

                                    Column(Modifier.padding(8.dp)) {
                                        Text(
                                            new.title ?: "",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Justify
                                        )

                                        Text(
                                            new.description ?: "",
                                            maxLines = 4,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Justify
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
