package com.example.android.strikingarts.data

import androidx.compose.ui.graphics.Color
import com.example.android.strikingarts.domain.model.AssetAudioAttributes
import com.example.android.strikingarts.domain.model.AudioAttributes
import com.example.android.strikingarts.domain.model.Combo
import com.example.android.strikingarts.domain.model.MovementType
import com.example.android.strikingarts.domain.model.SilenceAudioAttributes
import com.example.android.strikingarts.domain.model.Technique
import com.example.android.strikingarts.domain.model.TechniqueType
import com.example.android.strikingarts.domain.model.UriAudioAttributes
import com.example.android.strikingarts.domain.model.Workout
import com.example.android.strikingarts.domain.model.WorkoutResult
import com.example.android.strikingarts.domain.model.toImmutableList
import java.time.LocalDate

val jabAudioAttributes = UriAudioAttributes(
    id = 1, name = "jab.m4a", audioString = "content://jab.m4a", durationMillis = 398
)
val crossAudioAttributes = UriAudioAttributes(
    id = 2, name = "cross.m4a", audioString = "content://cross.m4a", durationMillis = 893
)
val leadHookAudioAttributes = UriAudioAttributes(
    id = 3, name = "lead_hook.m4a", audioString = "content://lead_hook.m4a", durationMillis = 708
)

val rearHookAudioAttributes = UriAudioAttributes(
    id = 4, name = "rear_hook.m4a", audioString = "content://rear_hook.m4a", durationMillis = 807
)

val assetAudioAttributes = AssetAudioAttributes(
    id = 5, name = "asset", audioString = "audio/techniques/asset.m4a", durationMillis = 777
)


val audioAttributesList: List<AudioAttributes> =
    listOf(jabAudioAttributes, crossAudioAttributes, leadHookAudioAttributes, assetAudioAttributes)

val jab = Technique(
    id = 1,
    name = "Jab",
    num = "1",
    audioAttributes = jabAudioAttributes,
    movementType = MovementType.OFFENSE,
    techniqueType = TechniqueType.PUNCH,
    color = "0"
)

val cross = Technique(
    id = 2,
    name = "Cross",
    num = "2",
    audioAttributes = crossAudioAttributes,
    movementType = MovementType.OFFENSE,
    techniqueType = TechniqueType.PUNCH,
    color = "0"
)

val leadHook = Technique(
    id = 3,
    name = "Lead Hook",
    num = "3",
    audioAttributes = leadHookAudioAttributes,
    movementType = MovementType.OFFENSE,
    techniqueType = TechniqueType.PUNCH,
    color = "0"
)

val stepBack = Technique(
    id = 4,
    name = "Step Back",
    audioAttributes = SilenceAudioAttributes,
    movementType = MovementType.DEFENSE,
    techniqueType = TechniqueType.FOOTWORK,
    color = Color.Red.toString()
)

val stepForward = Technique(
    id = 5,
    name = "Step Forward",
    audioAttributes = SilenceAudioAttributes,
    movementType = MovementType.DEFENSE,
    techniqueType = TechniqueType.FOOTWORK,
    color = Color.Red.toString()
)

val leadHighKick = Technique(
    id = 6,
    name = "Lead High Kick",
    audioAttributes = SilenceAudioAttributes,
    movementType = MovementType.OFFENSE,
    techniqueType = TechniqueType.KICK,
    color = "0"
)

val rearHighKick = Technique(
    id = 7,
    name = "Rear High Kick",
    audioAttributes = SilenceAudioAttributes,
    movementType = MovementType.OFFENSE,
    techniqueType = TechniqueType.KICK,
    color = "0"
)

val spearElbow = Technique(
    id = 8,
    name = "Spear Elbow",
    audioAttributes = SilenceAudioAttributes,
    movementType = MovementType.OFFENSE,
    techniqueType = TechniqueType.ELBOW,
    color = "0"
)

val slashingElbow = Technique(
    id = 8,
    name = "Slashing Elbow",
    audioAttributes = SilenceAudioAttributes,
    movementType = MovementType.OFFENSE,
    techniqueType = TechniqueType.ELBOW,
    color = "0"
)

val listOfTechniques =
    listOf(jab, cross, leadHook, stepBack, stepForward, leadHighKick, rearHighKick)

val jabCrossJab = Combo(
    id = 1,
    name = "Jab Cross Jab",
    desc = "1 2 1",
    delayMillis = 3000,
    techniqueList = listOf(jab, cross, jab).toImmutableList()
)

val crossStepBackCrossLeadHook = Combo(
    id = 2,
    name = "Cross Step Back Cross LeadHook",
    desc = "2 step back 2 3",
    delayMillis = 5000,
    techniqueList = listOf(cross, stepBack, cross, leadHook).toImmutableList()
)

val stepBackLeadHighKick = Combo(
    id = 3,
    name = "step back lead high kick",
    desc = "step back, LHK",
    delayMillis = 1000,
    techniqueList = listOf(stepBack, leadHighKick).toImmutableList()
)

val stepForwardSpearElbow = Combo(
    id = 3,
    name = "Step Forward, Spear Elbow",
    desc = "Make 'em bleed",
    delayMillis = 3000,
    techniqueList = listOf(stepForward, spearElbow).toImmutableList()
)

val rearHighKickStepForwardSlashingElbow = Combo(
    id = 3,
    name = "Rear high kick, Step forward, Slashing elbow",
    desc = "kick, close the distance, cut",
    delayMillis = 10000,
    techniqueList = listOf(rearHighKick, stepForward, slashingElbow).toImmutableList()
)

val workout1 = Workout(
    id = 1,
    name = "First Workout",
    rounds = 3,
    roundLengthSeconds = 180,
    restLengthSeconds = 60,
    subRounds = 2,
    comboList = listOf(stepBackLeadHighKick).toImmutableList()
)

val workout2 = Workout(
    id = 2,
    name = "Second Workout",
    rounds = 5,
    roundLengthSeconds = 300,
    restLengthSeconds = 60,
    subRounds = 3,
    comboList = listOf(
        stepForwardSpearElbow, rearHighKickStepForwardSlashingElbow
    ).toImmutableList()
)

val workoutResultSuccess1 = WorkoutResult(
    workoutId = 1,
    workoutName = "Finished 1",
    isWorkoutAborted = false,
    epochDay = LocalDate.now().toEpochDay()
)

val workoutResultSuccess2 = WorkoutResult(
    workoutId = 2,
    workoutName = "Finished 2",
    isWorkoutAborted = false,
    epochDay = LocalDate.now().minusDays(1L).toEpochDay()
)

val workoutResultFailure1 = WorkoutResult(
    workoutId = 3,
    workoutName = "Aborted 1",
    isWorkoutAborted = true,
    epochDay = LocalDate.now().toEpochDay()
)

val workoutResultFailure2 = WorkoutResult(
    workoutId = 4,
    workoutName = "Aborted 2",
    isWorkoutAborted = false,
    epochDay = LocalDate.now().minusDays(2L).toEpochDay()
)

val workoutResultList = listOf(
    workoutResultSuccess1, workoutResultSuccess2, workoutResultFailure1, workoutResultFailure2
)