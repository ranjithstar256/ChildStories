package tm.ranjith.childstories

data class PlaylistResponse(
    val items: List<PlaylistItem>,
    val nextPageToken: String?,
    val prevPageToken: String?
)

data class PlaylistItem(
    val snippet: Snippet
)

data class Snippet(
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

data class ThumbnailUrl(
    val url: String
)
