package com.example.android.strikingarts.database.repository

import com.example.android.strikingarts.database.dao.TechniqueDao
import com.example.android.strikingarts.database.entity.Technique
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TechniqueRepository @Inject constructor (private val techniqueDao: TechniqueDao) {
    val techniqueList: Flow<List<Technique>> = techniqueDao.getTechniqueList()

    suspend fun getTechnique(id: Long) = techniqueDao.getTechnique(id)

    suspend fun insert(technique: Technique) {
        techniqueDao.insert(technique)
    }

    suspend fun update(technique: Technique) {
        techniqueDao.update(technique)
    }
}