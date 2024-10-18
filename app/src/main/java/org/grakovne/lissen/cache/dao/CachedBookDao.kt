package org.grakovne.lissen.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import org.grakovne.lissen.cache.entity.BookChapterEntity
import org.grakovne.lissen.cache.entity.BookEntity
import org.grakovne.lissen.cache.entity.BookFileEntity
import org.grakovne.lissen.cache.entity.CachedBookEntity
import org.grakovne.lissen.cache.entity.MediaProgressEntity

@Dao
interface CachedBookDao {

    @Transaction
    suspend fun upsertCachedBook(cachedBook: CachedBookEntity) {
        upsertBook(cachedBook.detailedBook)
        upsertBookFiles(cachedBook.files)
        upsertBookChapters(cachedBook.chapters)
        cachedBook.progress?.let { upsertMediaProgress(it) }
    }

    @Transaction
    @Query("SELECT * FROM detailed_books ORDER BY title LIMIT :pageSize OFFSET :pageNumber * :pageSize")
    suspend fun fetchCachedBooks(pageNumber: Int, pageSize: Int): List<CachedBookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBook(book: BookEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBookFiles(files: List<BookFileEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBookChapters(chapters: List<BookChapterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMediaProgress(progress: MediaProgressEntity)

    @Update
    suspend fun updateMediaProgress(progress: MediaProgressEntity)

}