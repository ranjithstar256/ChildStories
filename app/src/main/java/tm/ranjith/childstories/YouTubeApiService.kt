package tm.ranjith.childstories

import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String = "snippet",
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int = 50,
        @Query("pageToken") pageToken: String? = null, // Add pageToken
        @Query("key") apiKey: String
    ): PlaylistResponse
}
