package tm.ranjith.childstories


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tm.ranjith.childstories.ui.theme.ChildStoriesTheme

@Composable
fun App(apiKey: String) {
    ChildStoriesTheme {
        val viewModel = PlaylistViewModel()
        Column(Modifier.background(Color.Black)) {


        Text("குழந்தைகளுக்கான கதைகள்", color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(16.dp))

        MainScreen(viewModel = viewModel, apiKey = apiKey)
    }
    }
}
