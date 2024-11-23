package tm.ranjith.childstories

import android.R.attr.apiKey
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pierfrancescosoffritti.androidyoutubeplayer.BuildConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // val apiKey = BuildConfig.YOUTUBE_API_KEY
        setContent {
            App(apiKey = "AIzaSyA5HkX6HukoaVrCzpxHCD6OA_60dhclbUY")
        }
    }
}


/*

package tm.ranjith.childstories


import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tm.ranjith.childstories.ui.theme.ChildStoriesTheme
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChildStoriesTheme {
                Column(Modifier.padding(16.dp).fillMaxSize()) {
                    App("AIzaSyA5HkX6HukoaVrCzpxHCD6OA_60dhclbUY")
                }
            }
        }
    }
}


@Composable
fun VideoList(videos: List<Video>, onVideoClick: (Video) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        videos.forEach { video ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onVideoClick(video) } // Notify parent of the clicked video
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Thumbnail image
                Image(
                    painter = rememberAsyncImagePainter(video.thumbnailUrl),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                // Video title
                Text(
                    text = video.title,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}



data class Video(val title: String, val videoId: String, val thumbnailUrl: String)


*/
/*
@Composable
fun YouTubePlayer(videoId: String, backAction: () -> Unit) {
    Column {
        Button(onClick = backAction) {
            Text("Back to Videos")
        }
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    webChromeClient = WebChromeClient()
                    loadUrl("https://www.youtube.com/embed/$videoId?autoplay=1&modestbranding=1")
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
*//*


@Composable
fun YouTubePlayer(videoId: String, backAction: () -> Unit) {
    val context = LocalContext.current

    Column {
        Button(onClick = backAction) {
            Text("Back")
        }
        AndroidView(
            factory = {
                YouTubePlayerView(context).apply {
                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(videoId, 0f)
                        }
                    })
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun MainScreen(viewModel: PlaylistViewModel, apiKey: String) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.CategoryList) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var selectedVideoId by remember { mutableStateOf<String?>(null) }

    // Updated categories list
    val categories = listOf(
        Category(
            name = "அக்பர் பீர்பால் கதைகள்",
            playlistId = "PL-QV9a0jbd_34e1mWCALcZUYFwRoYvWCB",
            thumbnailUrl = null
        ),
        Category(
            name = "பஞ்சதந்திர கதைகள்",
            playlistId = "PL-QV9a0jbd_21rRMO6W7OMq76sGm6ZiYv",
            thumbnailUrl = null
        ),
        Category(
            name = "தெனாலிராமன் கதைகள்",
            playlistId = "PL-QV9a0jbd_0nOgg_dAHgCKhkUSEq2DHJ",
            thumbnailUrl = null
        ),
        Category(
            name = "விக்கிரமாதித்தன் கதைகள்",
            playlistId = "PL-QV9a0jbd_3-1_W68lYVvZYXjEBa6uKO",
            thumbnailUrl = null
        ),
        Category(
            name = "மரியாதை ராமன் கதைகள்",
            playlistId = "PL-QV9a0jbd_08yui3Bg_ruHmPef-ZP0QK",
            thumbnailUrl = null
        ),
        Category(
            name = "நண்டு சொன்ன கதைகள்",
            playlistId = "PL-QV9a0jbd_34AsGzd1yQKbXDpZTr0aZN",
            thumbnailUrl = null
        ),
        Category(
            name = "ஜென் கதைகள்",
            playlistId = "PL-QV9a0jbd_3dthtbDNkcmvKKYpK3m3ht",
            thumbnailUrl = null
        ),
        Category(
            name = "நீதிக் கதைகள்",
            playlistId = "PL-QV9a0jbd_1pevGXjYAafe2WErfs4TrR",
            thumbnailUrl = null
        ),
        Category(
            name = "பரமார்த்த குரு கதைகள்",
            playlistId = "PL-QV9a0jbd_0YzMsl0MFeNqL7VUoineYL",
            thumbnailUrl = null
        ),
        Category(
            name = "காமராஜர் கதைகள்",
            playlistId = "PL-QV9a0jbd_3GBMibiCWd4gZ8_6y-qQOd",
            thumbnailUrl = null
        )
    )

    when (currentScreen) {
        is Screen.CategoryList -> {
            CategoryList(categories = categories) { category ->
                selectedCategory = category
                viewModel.fetchPlaylist(apiKey, category.playlistId)
                currentScreen = Screen.VideoList
            }
        }
        is Screen.VideoList -> {
            val videos by viewModel.videos.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()

            Column (Modifier.fillMaxSize()){
                Button(onClick = {
                    currentScreen = Screen.CategoryList
                    selectedCategory = null
                }) {
                    Text("பிரிவுகள்") // "Categories" in Tamil
                }

                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    VideoList(videos = videos) { video ->
                        selectedVideoId = video.videoId
                        currentScreen = Screen.Player
                    }
                }
            }
        }
        is Screen.Player -> {
            YouTubePlayer(videoId = selectedVideoId!!) {
                currentScreen = Screen.VideoList
                selectedVideoId = null
            }
        }
    }
}

// Define the different screens
sealed class Screen {
    object CategoryList : Screen()
    object VideoList : Screen()
    object Player : Screen()
}


@Composable
fun App(apiKey: String) {
    val viewModel = remember { PlaylistViewModel() }
    MainScreen(viewModel = viewModel, apiKey = apiKey)
}



@Composable
fun CategoryList(categories: List<Category>, onCategoryClick: (Category) -> Unit) {
    LazyColumn {
        items(categories) { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCategoryClick(category) }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (category.thumbnailUrl != null) {
                    Image(
                        painter = rememberAsyncImagePainter(category.thumbnailUrl),
                        contentDescription = category.name,
                        modifier = Modifier.size(100.dp)
                    )
                } else {
                    // Placeholder if no thumbnail is available
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Image",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
*/
