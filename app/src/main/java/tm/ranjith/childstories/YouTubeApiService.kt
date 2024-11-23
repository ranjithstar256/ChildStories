package tm.ranjith.childstories

import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    // Function to get playlist items (videos)
    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String = "snippet",
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int = 50,
        @Query("pageToken") pageToken: String? = null,
        @Query("key") apiKey: String
    ): PlaylistResponse

    // Function to get playlist details (thumbnails)
    @GET("playlists")
    suspend fun getPlaylistDetails(
        @Query("part") part: String = "snippet",
        @Query("id") playlistId: String,
        @Query("key") apiKey: String
    ): PlaylistsResponse
}


/*
package tm.ranjith.childstories

import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    // Existing function to get playlist items
    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String = "snippet",
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int = 50,
        @Query("pageToken") pageToken: String? = null,
        @Query("key") apiKey: String
    ): PlaylistResponse

    // New function to get playlist details (thumbnails)
    @GET("playlists")
    suspend fun getPlaylistDetails(
        @Query("part") part: String = "snippet",
        @Query("id") playlistId: String,
        @Query("key") apiKey: String
    ): PlaylistsResponse
}
*/
