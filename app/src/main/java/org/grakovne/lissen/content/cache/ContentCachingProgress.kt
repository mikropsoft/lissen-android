package org.grakovne.lissen.content.cache

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.grakovne.lissen.domain.DetailedItem
import org.grakovne.lissen.viewmodel.CacheStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentCachingProgress @Inject constructor() {
    private val _statusFlow = MutableSharedFlow<Pair<DetailedItem, CacheStatus>>(replay = 1)
    val statusFlow = _statusFlow.asSharedFlow()

    suspend fun emit(item: DetailedItem, progress: CacheStatus) {
        _statusFlow.emit(item to progress)
    }
}
