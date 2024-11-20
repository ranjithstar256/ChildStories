package tm.ranjith.childstories
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaylistViewModel : ViewModel() {

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchPlaylist(apiKey: String, playlistId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val allVideos = mutableListOf<Video>()
            var nextPageToken: String? = null

            while (true) {
                val response = RetrofitInstance.api.getPlaylistItems(
                    apiKey = apiKey,
                    playlistId = playlistId,
                    pageToken = nextPageToken
                )
                val videos = response.items.map {
                    Video(
                        title = it.snippet.title,
                        videoId = it.snippet.resourceId.videoId,
                        thumbnailUrl = it.snippet.thumbnails.medium.url
                    )
                }
                allVideos.addAll(videos)
                nextPageToken = response.nextPageToken
                if (nextPageToken == null) break
            }

            _videos.value = allVideos
            _isLoading.value = false
        }
    }
}
