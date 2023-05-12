package com.example.android.strikingarts.data.mapper

import com.example.android.strikingarts.data.local.room.model.Combo
import com.example.android.strikingarts.data.local.room.model.ComboWithTechniques
import com.example.android.strikingarts.data.local.room.model.Technique
import com.example.android.strikingarts.data.local.room.model.Workout
import com.example.android.strikingarts.data.local.room.model.WorkoutWithCombos
import com.example.android.strikingarts.domain.model.ComboListItem
import com.example.android.strikingarts.domain.model.TechniqueListItem
import com.example.android.strikingarts.domain.model.WorkoutListItem

fun TechniqueListItem.toDataModelModel() = Technique(
    techniqueId = this.id,
    name = this.name,
    num = this.num,
    canBeFaint = this.canBeFaint,
    canBeBodyshot = this.canBeBodyshot,
    sound = this.sound,
    color = this.color,
    techniqueType = this.techniqueType,
    movementType = this.movementType
)

fun ComboListItem.toDataModelModel() = ComboWithTechniques(combo = Combo(
    comboId = id, name = name, description = desc, delay = delay
), techniques = techniqueList.list.map { it.toDataModelModel() })

fun WorkoutListItem.toDataModel() = WorkoutWithCombos(workout = Workout(
    workoutId = this.id,
    name = this.name,
    rounds = this.rounds,
    roundLengthMilli = this.roundLengthMilli,
    notificationIntervals = this.notificationIntervals,
    restsLengthMilli = this.restLengthMilli
), combos = this.comboList.map { it.toDataModelModel() })