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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChildStoriesTheme {
                Column(Modifier.padding(16.dp).fillMaxSize().verticalScroll(rememberScrollState())) {
                    App("yourkeyhere")
                }
            }
        }
    }
}


@Composable
fun VideoList(videos: List<Video>, onVideoClick: (Video) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
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
fun MainScreen(viewModel: PlaylistViewModel) {
    var selectedVideoId by remember { mutableStateOf<String?>(null) }
    val videos by viewModel.videos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (selectedVideoId != null) {
        YouTubePlayer(videoId = selectedVideoId!!) {
            selectedVideoId = null // Go back to the video list
        }
    } else if (isLoading) {
        // Show a loading indicator
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        VideoList(videos = videos) { video ->
            selectedVideoId = video.videoId
        }
    }
}


@Composable
fun App(apiKey: String) {
    val viewModel = PlaylistViewModel()

    // Fetch playlist videos when the app starts
    LaunchedEffect(Unit) {
        viewModel.fetchPlaylist(apiKey, "PL-QV9a0jbd_34e1mWCALcZUYFwRoYvWCB")
    }

    MainScreen(viewModel)
}
