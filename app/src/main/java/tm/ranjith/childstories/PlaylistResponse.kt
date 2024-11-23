package tm.ranjith.childstories

data class PlaylistResponse(
    val items: List<PlaylistItem>,
    val nextPageToken: String?
)

data class PlaylistItem(
    val snippet: PlaylistItemSnippet
)

data class PlaylistItemSnippet(
    val title: String,
    val resourceId: ResourceId,
    val thumbnails: Thumbnails
)

data class ResourceId(
    val videoId: String
)

data class Thumbnails(
    val medium: ThumbnailUrl
)



/*
package tm.ranjith.childstories


data class PlaylistsResponse(
    val items: List<PlaylistDetailItem>
)

data class PlaylistDetailItem(
    val snippet: PlaylistSnippet
)

data class PlaylistSnippet(
    val title: String,
    val thumbnails: PlaylistThumbnails
)

data class PlaylistThumbnails(
    val medium: ThumbnailUrl,
    val high: ThumbnailUrl
    // You can include other resolutions if needed
)

data class ThumbnailUrl(
    val url: String
)
*/
