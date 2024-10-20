package org.grakovne.lissen.content.cache.api

import android.net.Uri
import androidx.core.net.toUri
import org.grakovne.lissen.content.cache.CacheBookStorageProperties
import org.grakovne.lissen.content.cache.converter.CachedBookEntityConverter
import org.grakovne.lissen.content.cache.converter.CachedBookEntityDetailedConverter
import org.grakovne.lissen.content.cache.dao.CachedBookDao
import org.grakovne.lissen.domain.Book
import org.grakovne.lissen.domain.DetailedBook
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CachedBookRepository @Inject constructor(
    private val bookDao: CachedBookDao,
    private val properties: CacheBookStorageProperties,
    private val cachedBookEntityConverter: CachedBookEntityConverter,
    private val cachedBookEntityDetailedConverter: CachedBookEntityDetailedConverter
) {

    fun provideFileUri(bookId: String, fileId: String): Uri = properties
        .provideMediaCachePatch(bookId, fileId)
        .toUri()

    fun provideBookCover(bookId: String): File = properties.provideBookCoverPath(bookId)

    suspend fun removeBook(bookId: String) {
        bookDao
            .fetchBook(bookId)
            ?.let { bookDao.deleteBook(it) }
    }

    suspend fun cacheBook(book: DetailedBook) {
        bookDao.upsertCachedBook(book)
    }

    suspend fun fetchBooks(
        pageNumber: Int,
        pageSize: Int
    ): List<Book> = bookDao
        .fetchCachedBooks(pageNumber = pageNumber, pageSize = pageSize)
        .map { cachedBookEntityConverter.apply(it) }

    suspend fun fetchBook(
        bookId: String
    ): DetailedBook? = bookDao
        .fetchCachedBook(bookId)
        ?.let { cachedBookEntityDetailedConverter.apply(it) }
}