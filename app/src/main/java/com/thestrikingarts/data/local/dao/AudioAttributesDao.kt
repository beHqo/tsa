package com.thestrikingarts.data.local.dao

import com.thestrikingarts.LocalDatabase
import com.thestrikingarts.data.mapper.toDomainModel
import com.thestrikingarts.di.DefaultDispatcher
import com.thestrikingarts.di.IoDispatcher
import com.thestrikingarts.domain.model.AudioAttributes
import com.thestrikingarts.domain.model.UriAudioAttributes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioAttributesDao @Inject constructor(
    private val db: LocalDatabase,
    @IoDispatcher private val ioDispatchers: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatchers: CoroutineDispatcher
) {
    private val queries = db.audioAttributesQueries

    suspend fun insert(audioAttributes: AudioAttributes): Long = withContext(ioDispatchers) {
        db.transactionWithResult {
            queries.insert(
                name = audioAttributes.name,
                durationMillis = audioAttributes.durationMillis,
                path = audioAttributes.audioString,
            )

            return@transactionWithResult queries.lastInsertedRowId().executeAsOne()
        }
    }

    suspend fun update(audioAttributes: UriAudioAttributes): Long = withContext(ioDispatchers) {
        db.transactionWithResult {
            queries.update(
                audioAttributesId = audioAttributes.id,
                name = audioAttributes.name,
                durationMillies = audioAttributes.durationMillis,
                path = audioAttributes.audioString
            )

            return@transactionWithResult queries.affectedRow().executeAsOne()
        }
    }

    suspend fun getAudioAttributesByPath(path: String): AudioAttributes? =
        withContext(ioDispatchers) {
            val selected = queries.getAudioAttributesByPath(path).executeAsOneOrNull()
                ?: return@withContext null

            return@withContext withContext(defaultDispatchers) { selected.toDomainModel() }
        }

    suspend fun getAudioAttributesById(id: Long): AudioAttributes? = withContext(ioDispatchers) {
        val selected =
            queries.getAudioAttributesById(id).executeAsOneOrNull() ?: return@withContext null

        return@withContext withContext(defaultDispatchers) { selected.toDomainModel() }
    }
}