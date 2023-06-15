package com.erkindilekci.newssphere.presentation.savednewsscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.erkindilekci.newssphere.R
import com.erkindilekci.newssphere.presentation.util.BottomNavigationBar
import com.erkindilekci.newssphere.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedNewsScreen(
    navController: NavController,
    viewModel: SavedNewsScreenViewModel = hiltViewModel()
) {
    val savedNews = viewModel.savedNews.collectAsState(initial = emptyList()).value

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Saved News",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {

        if (savedNews.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                items(savedNews) { article ->
                    val dismissState = rememberDismissState()
                    val dismissDirection = dismissState.dismissDirection
                    val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
                    if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                        viewModel.deleteArticle(article)
                    }

                    val degrees by animateFloatAsState(
                        targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f
                    )

                    SwipeToDismiss(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = { RedBackground(degrees = degrees) },
                        dismissContent = {
                            Card(
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate("${Screen.DetailScreen.route}/${article.title}")
                                    },
                            ) {
                                Column {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(16f / 9f),
                                        painter = rememberImagePainter(
                                            data = article.urlToImage,
                                            builder = {
                                                placeholder(R.drawable.placeholder)
                                                error(R.drawable.placeholder)
                                            }
                                        ),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillWidth
                                    )
                                    Column(Modifier.padding(8.dp)) {
                                        Text(
                                            article.title ?: "",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Justify
                                        )

                                        Text(
                                            article.description ?: "",
                                            maxLines = 4,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Justify
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxSize()

            .background(Color.Red)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees),
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = Color.White
        )
    }
}
