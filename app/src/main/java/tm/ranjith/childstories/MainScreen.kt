package tm.ranjith.childstories

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: PlaylistViewModel, apiKey: String) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.CategoryList) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var selectedVideoId by remember { mutableStateOf<String?>(null) }

    // Initial categories without thumbnails
    val initialCategories = listOf(
        Category(
            name = "அக்பர் பீர்பால் கதைகள்",
            playlistId = "PL-QV9a0jbd_34e1mWCALcZUYFwRoYvWCB"
        ),
        Category(
            name = "பஞ்சதந்திர கதைகள்",
            playlistId = "PL-QV9a0jbd_21rRMO6W7OMq76sGm6ZiYv"
        ),
        Category(
            name = "தெனாலிராமன் கதைகள்",
            playlistId = "PL-QV9a0jbd_0nOgg_dAHgCKhkUSEq2DHJ"
        ),
        Category(
            name = "விக்கிரமாதித்தன் கதைகள்",
            playlistId = "PL-QV9a0jbd_3-1_W68lYVvZYXjEBa6uKO"
        ),
        Category(
            name = "மரியாதை ராமன் கதைகள்",
            playlistId = "PL-QV9a0jbd_08yui3Bg_ruHmPef-ZP0QK"
        ),
        Category(
            name = "நண்டு சொன்ன கதைகள்",
            playlistId = "PL-QV9a0jbd_34AsGzd1yQKbXDpZTr0aZN"
        ),
        Category(
            name = "ஜென் கதைகள்",
            playlistId = "PL-QV9a0jbd_3dthtbDNkcmvKKYpK3m3ht"
        ),
        Category(
            name = "நீதிக் கதைகள்",
            playlistId = "PL-QV9a0jbd_1pevGXjYAafe2WErfs4TrR"
        ),
        Category(
            name = "பரமார்த்த குரு கதைகள்",
            playlistId = "PL-QV9a0jbd_0YzMsl0MFeNqL7VUoineYL"
        ),
        Category(
            name = "காமராஜர் கதைகள்",
            playlistId = "PL-QV9a0jbd_3GBMibiCWd4gZ8_6y-qQOd"
        )
    )

    // Fetch categories with thumbnails when the composable is first launched
    LaunchedEffect(Unit) {
        viewModel.fetchCategories(apiKey, initialCategories)
    }

    // Observe the categories with thumbnails from the ViewModel
    val categories by viewModel.categories.collectAsState()

    Scaffold(
         Modifier.fillMaxSize()
    ) {

        when (currentScreen) {
            is Screen.CategoryList -> {
                if (categories.isEmpty()) {
                    // Show a loading indicator while fetching categories
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {

                    CategoryList(categories = categories) { category ->
                        selectedCategory = category
                        viewModel.fetchPlaylist(apiKey, category.playlistId)
                        currentScreen = Screen.VideoList
                    }
                }
            }
            is Screen.VideoList -> {
                val videos by viewModel.videos.collectAsState()
                val isLoading by viewModel.isLoading.collectAsState()

                Column {
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
}

// Define the different screens
sealed class Screen {
    object CategoryList : Screen()
    object VideoList : Screen()
    object Player : Screen()
}
