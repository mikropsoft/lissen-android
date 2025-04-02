package org.grakovne.lissen.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.grakovne.lissen.content.NewContentCachingService
import org.grakovne.lissen.content.cache.CacheProgressBus
import org.grakovne.lissen.content.cache.ContentCachingForegroundService
import org.grakovne.lissen.domain.ContentCachingTask
import org.grakovne.lissen.domain.DownloadOption
import org.grakovne.lissen.persistence.preferences.LissenSharedPreferences
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class ContentCachingModelView @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cacheProgressBus: CacheProgressBus,
    private val contentCachingService: NewContentCachingService,
    private val preferences: LissenSharedPreferences,
) : ViewModel() {

    private val _bookCachingProgress = mutableMapOf<String, MutableStateFlow<CacheProgress>>()

    init {
        viewModelScope.launch {
            cacheProgressBus.progressFlow.collect { (itemId, progress) ->
                val flow = _bookCachingProgress.getOrPut(itemId) {
                    MutableStateFlow(progress)
                }
                flow.value = progress
            }
        }
    }

    fun requestCache(
        mediaItemId: String,
        currentPosition: Double,
        option: DownloadOption,
    ) {
        val task = ContentCachingTask(
            itemId = mediaItemId,
            options = option,
            currentPosition = currentPosition,
        )

        val intent = Intent(context, ContentCachingForegroundService::class.java).apply {
            putExtra(ContentCachingForegroundService.CACHING_TASK_EXTRA, task as Serializable)
        }

        context.startForegroundService(intent)
    }

    fun getCacheProgress(bookId: String) = _bookCachingProgress
        .getOrPut(bookId) { MutableStateFlow(CacheProgress.Idle) }

    fun dropCache(bookId: String) {
        viewModelScope
            .launch {
                contentCachingService.dropCache(bookId)
                _bookCachingProgress.remove(bookId)
            }
    }

    fun toggleCacheForce() {
        when (localCacheUsing()) {
            true -> preferences.disableForceCache()
            false -> preferences.enableForceCache()
        }
    }

    fun localCacheUsing() = preferences.isForceCache()

    fun provideCacheState(bookId: String): LiveData<Boolean> =
        contentCachingService.hasMetadataCached(bookId)
}
