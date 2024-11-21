package com.github.tsa.domain.workoutresult

import com.github.tsa.domain.model.WorkoutConclusion
import com.github.tsa.domain.model.WorkoutResult
import com.github.tsa.domainandroid.usecase.javatime.GetEpochDayForToday
import javax.inject.Inject

class UpsertWorkoutResultUseCase @Inject constructor(
    private val workoutResultRepository: WorkoutResultCacheRepository,
    private val retrieveLastExecutedWorkoutResultUseCase: RetrieveLastExecutedWorkoutResultUseCase,
    private val updateWorkoutResult: UpdateWorkoutResult,
    private val getEpochDayForToday: GetEpochDayForToday
) {
    suspend operator fun invoke(workoutId: Long, workoutName: String, isWorkoutAborted: Boolean) {
        val workoutConclusion = if (isWorkoutAborted) WorkoutConclusion.Aborted(false)
        else WorkoutConclusion.Successful

        if (!isWorkoutAborted) {
            val previouslyFailed = retrieveLastExecutedWorkoutResultUseCase.failed()

            if (previouslyFailed?.workoutId == workoutId) {
                updateWorkoutResult(previouslyFailed.id, WorkoutConclusion.Aborted(true))
            }
        }

        workoutResultRepository.insert(
            WorkoutResult(
                workoutId = workoutId,
                workoutName = workoutName,
                conclusion = workoutConclusion,
                epochDay = getEpochDayForToday()
            )
        )
    }
}