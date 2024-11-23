package tm.ranjith.childstories

import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun YouTubePlayer(videoId: String, backAction: () -> Unit) {
    val context = LocalContext.current

    Column(Modifier.verticalScroll(rememberScrollState())) {
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
/*

@Composable
fun YouTubePlayer(videoId: String, backAction: () -> Unit) {
    val context = LocalContext.current

    Column {
        Button(onClick = backAction) {
            Text("பின் செல்ல") // "Go Back" in Tamil
        }
        AndroidView(
            factory = {
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
*/
