package com.example.android.strikingarts.domain.model

import androidx.compose.runtime.Immutable
import com.example.android.strikingarts.R

@Immutable
data class TechniqueListItem(
    val id: Long = 0L,
    val name: String = "",
    val num: String = "",
    val canBeBodyshot: Boolean = false,
    val canBeFaint: Boolean = false,
    val sound: Int = R.raw.shoombool,
    val color: String = "0",
    val techniqueType: String = "",
    val movementType: String = "",
    val imageRes: Int = R.drawable.none_color
)