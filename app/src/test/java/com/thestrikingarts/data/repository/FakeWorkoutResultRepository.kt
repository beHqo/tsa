package com.thestrikingarts.data.repository

import com.thestrikingarts.data.workoutResultList
import com.thestrikingarts.domain.model.WorkoutConclusion
import com.thestrikingarts.domain.model.WorkoutResult
import com.thestrikingarts.domain.workoutresult.WorkoutResultCacheRepository

class FakeWorkoutResultRepository : WorkoutResultCacheRepository {
    private val data = workoutResultList.toMutableList()

    override suspend fun lastSuccessfulWorkoutResult(): WorkoutResult? =
        data.filter { it.conclusion is WorkoutConclusion.Successful }.maxByOrNull { it.epochDay }

    override suspend fun lastFailedWorkoutResult(): WorkoutResult? =
        data.filter { it.conclusion.isWorkoutFailed() }.maxByOrNull { it.epochDay }

    override suspend fun getWorkoutResultsInRange(
        fromEpochDay: Long, toEpochDay: Long
    ): List<WorkoutResult> =
        data.filter { it.epochDay in fromEpochDay..toEpochDay }

    override suspend fun getWorkoutResultsByDate(epochDay: Long): List<WorkoutResult> =
        data.filter { it.epochDay == epochDay }

    override suspend fun insert(workoutResult: WorkoutResult) {
        data += workoutResult
    }

    override suspend fun update(workoutResultId: Long, workoutConclusion: WorkoutConclusion): Long {
        var toBeUpdated = WorkoutResult()
        var index = -1

        for ((i, workoutResult) in data.withIndex())
            if (workoutResult.id == workoutResultId) {
                index = i
                toBeUpdated = workoutResult

                break
            }

        return if (index != -1) {
            data.remove(toBeUpdated)
            data.add(index, toBeUpdated.copy(conclusion = workoutConclusion))

            1
        } else 0
    }

    fun getLastInsertedOrDefault(): WorkoutResult = data.lastOrNull() ?: WorkoutResult()
}