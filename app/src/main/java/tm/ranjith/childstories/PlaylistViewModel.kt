package tm.ranjith.childstories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaylistViewModel : ViewModel() {

    // Videos in the selected playlist
    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    // Loading state for videos
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Categories with thumbnails
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    // Fetch videos in a playlist
    fun fetchPlaylist(apiKey: String, playlistId: String) {
        viewModelScope.launch {
            try {
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
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fetch categories with thumbnails
    fun fetchCategories(apiKey: String, categoriesList: List<Category>) {
        viewModelScope.launch {
            try {
                val updatedCategories = categoriesList.map { category ->
                    val response = RetrofitInstance.api.getPlaylistDetails(
                        playlistId = category.playlistId,
                        apiKey = apiKey
                    )
                    val thumbnailUrl = response.items.firstOrNull()?.snippet?.thumbnails?.medium?.url
                    category.copy(thumbnailUrl = thumbnailUrl)
                }
                _categories.value = updatedCategories
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

/*
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
    // New property for categories with thumbnails
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories


    // Fetch videos for a given playlist ID
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

    // New function to fetch categories with thumbnails
    fun fetchCategories(apiKey: String, categoriesList: List<Category>) {
        viewModelScope.launch {
            try {
                val updatedCategories = categoriesList.map { category ->
                    val response = RetrofitInstance.api.getPlaylistDetails(
                        playlistId = category.playlistId,
                        apiKey = apiKey
                    )
                    val thumbnailUrl = response.items.firstOrNull()?.snippet?.thumbnails?.medium?.url
                    category.copy(thumbnailUrl = thumbnailUrl)
                }
                _categories.value = updatedCategories
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
// Existing data classes
data class Video(val title: String, val videoId: String, val thumbnailUrl: String)

*/
