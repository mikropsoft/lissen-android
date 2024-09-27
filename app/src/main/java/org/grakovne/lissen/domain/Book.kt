package org.grakovne.lissen.domain

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val duration: Int,
    val downloaded: Boolean
)
