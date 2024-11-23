package tm.ranjith.childstories

data class PlaylistsResponse(
    val items: List<PlaylistDetailItem>
)




data class PlaylistThumbnails(
    val medium: ThumbnailUrl,
    val high: ThumbnailUrl
)
