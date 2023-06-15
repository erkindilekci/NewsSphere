package com.erkindilekci.newssphere.presentation.listscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.erkindilekci.newssphere.R
import com.erkindilekci.newssphere.domain.model.News
import com.erkindilekci.newssphere.presentation.ui.theme.NewsSphereTheme
import com.erkindilekci.newssphere.util.Screen

@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListScreenViewModel = hiltViewModel()
) {
    val newsList by viewModel.getNews().collectAsState()

    ListScreenContent(navController, newsList)

}

@Composable
fun ListScreenContent(
    navController: NavController,
    news: List<News>
) {
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
                    "Top news",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
    {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(news) { new ->
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
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f),
                            painter = rememberImagePainter(
                                data = new.urlToImage,
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
                                new.title,
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

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    NewsSphereTheme {
        ListScreenContent(
            navController = rememberNavController(),
            news = arrayListOf(
                News(
                    "Title", "Content description", "", "",
                    "https://via.placeholder.com/540x300?text=yayocode.com",
                    ""
                ),
                News(
                    "Title2", "Content description", "", "",
                    "https://via.placeholder.com/540x300?text=yayocode.com",
                    ""
                ),
                News(
                    "Title2", "Content description", "", "",
                    "https://via.placeholder.com/540x300?text=yayocode.com",
                    ""
                )
            )
        )
    }
}