package tm.ranjith.childstories



data class PlaylistDetailItem(
    val snippet: PlaylistSnippet
)

data class PlaylistSnippet(
    val title: String,
    val thumbnails: PlaylistThumbnails
)

data class ThumbnailUrl(
    val url: String
)
