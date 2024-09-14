package com.example.android.strikingarts.data.repository

import com.example.android.strikingarts.data.local.dao.TechniqueDao
import com.example.android.strikingarts.domain.common.logger.DataLogger
import com.example.android.strikingarts.domain.interfaces.TechniqueCacheRepository
import com.example.android.strikingarts.domain.model.Technique
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "TechniqueRepository"

@Singleton
class TechniqueRepository @Inject constructor(private val techniqueDao: TechniqueDao) :
    TechniqueCacheRepository {
    private val logger = DataLogger(TAG)

    override val techniqueList = techniqueDao.getTechniqueList

    override suspend fun getTechnique(id: Long): Technique {
        val technique = techniqueDao.getTechnique(id)

        return if (technique == null) {
            logger.logRetrieveOperation(id, "getTechnique")

            Technique()
        } else technique
    }

    override suspend fun insert(technique: Technique, audioAttributesId: Long?) {
        val id = techniqueDao.insert(technique, audioAttributesId)

        logger.logInsertOperation(id, technique)
    }

    override suspend fun update(technique: Technique, audioAttributesId: Long?) {
        val affectedRows = techniqueDao.update(technique, audioAttributesId)

        logger.logUpdateOperation(affectedRows, technique.id, technique)
    }

    override suspend fun delete(id: Long): Long {
        val affectedRows = techniqueDao.delete(id)

        logger.logDeleteOperation(affectedRows, id)

        return affectedRows
    }

    override suspend fun deleteAll(idList: List<Long>): Long {
        val affectedRows = techniqueDao.deleteAll(idList)

        logger.logDeleteAllOperation(affectedRows, idList)

        return affectedRows
    }
}