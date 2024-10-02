package org.grakovne.lissen.client.audiobookshelf.model

data class AudioFileResponse(
    val index: Int,
    val ino: String,
    val duration: Double,
    val metadata: AudioFileMetadata,
    val metaTags: AudioFileTag?
)

data class AudioFileMetadata(
    val filename: String,
    val ext: String,
    val size: Long
)

data class AudioFileTag(
    val tagAlbum: String,
    val tagTitle: String
)