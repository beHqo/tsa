package com.example.android.strikingarts.ui.losersscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.strikingarts.domain.model.WorkoutListItem
import com.example.android.strikingarts.domain.usecase.winners.InsertTrainingDateUseCase
import com.example.android.strikingarts.domain.usecase.workout.RetrieveWorkoutUseCase
import com.example.android.strikingarts.ui.audioplayers.PlayerConstants
import com.example.android.strikingarts.ui.audioplayers.soundpool.SoundPoolWrapper
import com.example.android.strikingarts.ui.navigation.Screen.Arguments.LOSERS_WORKOUT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LosersViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val soundPoolWrapper: SoundPoolWrapper,
    private val retrieveWorkoutUseCase: RetrieveWorkoutUseCase,
    private val insertTrainingDateUseCase: InsertTrainingDateUseCase
) : ViewModel() {
    private val workoutId: Long = savedStateHandle[LOSERS_WORKOUT_ID] ?: 0

    private lateinit var workoutListItem: WorkoutListItem

    private val _loadingScreen = MutableStateFlow(false)
    val loadingScreen = _loadingScreen.asStateFlow()

    init {
        viewModelScope.launch { initialUiUpdate() }
    }

    private suspend fun initialUiUpdate() {
        val fetchWorkoutJob = viewModelScope.launch { fetchWorkout() }
        fetchWorkoutJob.join()

        insertAbortedWorkout()

        soundPoolWrapper.play(QUIT_SOUND)

        _loadingScreen.update { false }
    }

    private suspend fun fetchWorkout() {
        if (workoutId != 0L) workoutListItem =
            retrieveWorkoutUseCase(workoutId) else WorkoutListItem()
    }

    private suspend fun insertAbortedWorkout() {
        if (workoutId != 0L) insertTrainingDateUseCase(
            workoutId = workoutId, workoutName = workoutListItem.name, isWorkoutAborted = true
        )
    }

    override fun onCleared() {
        soundPoolWrapper.release()
        super.onCleared()
    }

    companion object {
        private const val QUIT_SOUND = PlayerConstants.ASSET_SESSION_EVENT_PATH_PREFIX + "quit.wav"
    }
}