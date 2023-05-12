package com.example.android.strikingarts.ui.techniquedetails

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.strikingarts.domain.common.ImmutableSet
import com.example.android.strikingarts.domain.model.TechniqueCategory.DEFENSE
import com.example.android.strikingarts.domain.model.TechniqueCategory.OFFENSE
import com.example.android.strikingarts.domain.model.TechniqueCategory.defenseTypes
import com.example.android.strikingarts.domain.model.TechniqueCategory.offenseTypes
import com.example.android.strikingarts.domain.model.TechniqueListItem
import com.example.android.strikingarts.domain.usecase.technique.RetrieveTechniqueUseCase
import com.example.android.strikingarts.domain.usecase.technique.UpsertTechniqueUseCase
import com.example.android.strikingarts.ui.components.TEXTFIELD_NAME_MAX_CHARS
import com.example.android.strikingarts.ui.navigation.Screen.Arguments.TECHNIQUE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TRANSPARENT_COLOR_VALUE = "0"

@HiltViewModel
class TechniqueDetailsViewModel @Inject constructor(
    private val retrieveTechniqueUseCase: RetrieveTechniqueUseCase,
    private val upsertTechniqueUseCase: UpsertTechniqueUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val techniqueId = savedStateHandle[TECHNIQUE_ID] ?: 0L

    private val technique = MutableStateFlow(TechniqueListItem())

    private val _loadingScreen = MutableStateFlow(true)
    private val _name = MutableStateFlow("")
    private val _num = MutableStateFlow("")
    private val _movementType = MutableStateFlow("")
    private val _techniqueType = MutableStateFlow("")
    private val _color = MutableStateFlow(TRANSPARENT_COLOR_VALUE)
    private val _techniqueTypeList = MutableStateFlow(ImmutableSet<String>())

    val loadingScreen = _loadingScreen.asStateFlow()
    val name = _name.asStateFlow()
    val num = _num.asStateFlow()
    val movementType = _movementType.asStateFlow()
    val techniqueType = _techniqueType.asStateFlow()
    val color = _color.asStateFlow()
    val techniqueTypeList = _techniqueTypeList.asStateFlow()

    init {
        initializeTechniqueAndDisplayState()
    }

    private fun initializeTechniqueAndDisplayState() {
        if (techniqueId == 0L) return else viewModelScope.launch {
            technique.update { retrieveTechniqueUseCase(techniqueId) }

            initialUiUpdate()
        }
    }

    private fun initialUiUpdate() {
        _name.update { savedStateHandle[NAME] ?: technique.value.name }
        _num.update { savedStateHandle[NUM] ?: technique.value.num }
        _movementType.update { savedStateHandle[MOVEMENT_TYPE] ?: technique.value.movementType }
        _techniqueType.update { savedStateHandle[TECHNIQUE_TYPE] ?: technique.value.techniqueType }
        _color.update { technique.value.color }
        _techniqueTypeList.update {
            ImmutableSet(
                if ((savedStateHandle[MOVEMENT_TYPE]
                        ?: technique.value.movementType) == DEFENSE
                ) defenseTypes.keys
                else offenseTypes.keys
            )
        }

        _loadingScreen.update { false }
    }

    fun onNameChange(value: String) {
        if (value.length <= TEXTFIELD_NAME_MAX_CHARS + 1) {
            _name.update { value }

            savedStateHandle[NAME] = value
        }
    }

    fun onNumChange(value: String) {
        if (value.isDigitsOnly()) {
            _num.update { value }

            savedStateHandle[NUM] = value
        }
    }

    fun onMovementTypeChange(newMovementType: String) {
        _movementType.update { newMovementType }
        _techniqueType.update { "" }
        _techniqueTypeList.update { ImmutableSet(if (newMovementType == DEFENSE) defenseTypes.keys else offenseTypes.keys) }
        if (newMovementType == DEFENSE) _num.update { "" }
        if (newMovementType == OFFENSE) _color.update { TRANSPARENT_COLOR_VALUE }

        savedStateHandle[MOVEMENT_TYPE] = newMovementType
    }

    fun onTechniqueTypeChange(newTechniqueType: String) {
        _techniqueType.update { newTechniqueType }
        savedStateHandle[TECHNIQUE_TYPE] = newTechniqueType
    }

    fun onColorChange(newColor: String) {
        _color.update { newColor }
    }

    fun insertOrUpdateItem() {
        viewModelScope.launch {
            upsertTechniqueUseCase(
                TechniqueListItem(
                    id = techniqueId,
                    name = _name.value,
                    num = _num.value,
                    techniqueType = _techniqueType.value,
                    movementType = _movementType.value,
                    color = color.value,
                    canBeBodyshot = _movementType.value == OFFENSE,
                    canBeFaint = _movementType.value == OFFENSE
                )
            )
        }
    }

    companion object {
        const val NAME = "name"
        const val NUM = "num"
        const val TECHNIQUE_TYPE = "technique_type"
        const val MOVEMENT_TYPE = "movement_type"
    }
}