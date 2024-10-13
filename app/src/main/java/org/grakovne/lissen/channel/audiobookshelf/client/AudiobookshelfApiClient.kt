package org.grakovne.lissen.channel.audiobookshelf.client

import org.grakovne.lissen.channel.audiobookshelf.model.LibraryItemIdResponse
import org.grakovne.lissen.channel.audiobookshelf.model.LibraryItemsResponse
import org.grakovne.lissen.channel.audiobookshelf.model.LibraryResponse
import org.grakovne.lissen.channel.audiobookshelf.model.LoginRequest
import org.grakovne.lissen.channel.audiobookshelf.model.LoginResponse
import org.grakovne.lissen.channel.audiobookshelf.model.MediaProgressResponse
import org.grakovne.lissen.channel.audiobookshelf.model.PlaybackSessionResponse
import org.grakovne.lissen.channel.audiobookshelf.model.RecentListeningResponse
import org.grakovne.lissen.channel.audiobookshelf.model.SyncProgressRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AudiobookshelfApiClient {

    @GET("/api/libraries")
    suspend fun getLibraries(): Response<LibraryResponse>

    @POST("/api/items/{itemId}/play")
    suspend fun startPlayback(
        @Path("itemId") itemId: String
    ): Response<PlaybackSessionResponse>

    @GET("/api/me/listening-stats")
    suspend fun getRecentItems(): Response<RecentListeningResponse>

    @POST("/api/session/{itemId}/sync")
    suspend fun syncProgress(
        @Path("itemId") itemId: String,
        @Body syncProgressRequest: SyncProgressRequest
    ): Response<Unit>

    @GET("/api/me/progress/{itemId}")
    @Headers("Cache-Control: no-cache")
    suspend fun getLibraryItemProgress(
        @Path("itemId") itemId: String
    ): Response<MediaProgressResponse>

    @GET("/api/items/{itemId}?expanded=1&include=progress")
    suspend fun getLibraryItem(
        @Path("itemId") itemId: String
    ): Response<LibraryItemIdResponse>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/libraries/{libraryId}/items")
    suspend fun getLibraryItems(
        @Path("libraryId") libraryId: String
    ): Response<LibraryItemsResponse>
}