package com.example.android.strikingarts.data.repository

import com.example.android.strikingarts.data.listOfTechniques
import com.example.android.strikingarts.domain.interfaces.TechniqueCacheRepository
import com.example.android.strikingarts.domain.model.SilenceAudioAttributes
import com.example.android.strikingarts.domain.model.Technique
import com.example.android.strikingarts.domain.model.UriAudioAttributes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeTechniqueRepository : TechniqueCacheRepository {
    private var lastAvailableIndex = -1L
    private val data = listOfTechniques.toMutableList()

    override val techniqueList: Flow<List<Technique>> = flowOf(data)

    init {
        lastAvailableIndex = (data.maxOfOrNull { it.id } ?: 0) + 1
    }

    override suspend fun getTechnique(id: Long): Technique =
        data.firstOrNull { it.id == id } ?: Technique()

    override suspend fun insert(technique: Technique, audioAttributesId: Long?) {
        data += technique.copy(
            id = lastAvailableIndex++,
            audioAttributes = if (audioAttributesId != null) UriAudioAttributes(id = audioAttributesId)
            else SilenceAudioAttributes
        )
    }

    override suspend fun update(technique: Technique, audioAttributesId: Long?) {
        val retrieved = data.firstOrNull { it.id == technique.id } ?: return

        data -= retrieved

        data += technique.copy(
            audioAttributes = if (audioAttributesId != null) UriAudioAttributes(id = audioAttributesId)
            else SilenceAudioAttributes
        )
    }

    override suspend fun delete(id: Long): Long = if (data.removeIf { it.id == id }) 1 else 0

    override suspend fun deleteAll(idList: List<Long>): Long {
        val tobeDeleted = data.filter { it.id in idList }

        val idListSize = idList.size
        return if (tobeDeleted.size == idListSize) {
            data.removeAll(tobeDeleted)

            idListSize.toLong()
        } else 0L
    }

    fun doesDatabaseContainTechniqueWithIdOf(id: Long): Boolean = data.any { it.id == id }

    fun getLastInsertedTechnique(): Technique = data.last()
}