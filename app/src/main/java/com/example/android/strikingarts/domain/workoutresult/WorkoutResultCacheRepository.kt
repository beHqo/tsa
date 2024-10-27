package com.example.android.strikingarts.domain.workoutresult

import com.example.android.strikingarts.domain.model.WorkoutConclusion
import com.example.android.strikingarts.domain.model.WorkoutResult

interface WorkoutResultCacheRepository {
    suspend fun lastSuccessfulWorkoutResult(): WorkoutResult?
    suspend fun lastFailedWorkoutResult(): WorkoutResult?
    suspend fun getWorkoutResultsInRange(
        fromEpochDay: Long, toEpochDay: Long
    ): List<WorkoutResult>
    suspend fun getWorkoutResultsByDate(epochDay: Long): List<WorkoutResult>
    suspend fun insert(workoutResult: WorkoutResult)
    suspend fun update(workoutResultId: Long, workoutConclusion: WorkoutConclusion): Long
}