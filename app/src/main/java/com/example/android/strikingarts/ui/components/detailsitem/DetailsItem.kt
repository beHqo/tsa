package com.example.android.strikingarts.ui.components.detailsitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.android.strikingarts.R
import com.example.android.strikingarts.ui.components.ColorSample
import com.example.android.strikingarts.ui.components.PrimaryText
import com.example.android.strikingarts.ui.components.SelectableHexagonButton
import com.example.android.strikingarts.ui.theme.designsystemmanager.ColorManager
import com.example.android.strikingarts.ui.theme.designsystemmanager.ContentAlphaManager

@Composable
fun DetailsItem(
    modifier: Modifier = Modifier,
    startText: String,
    onClick: () -> Unit,
    endSideSlot: @Composable () -> Unit
) = Box(
    contentAlignment = Alignment.CenterEnd,
    modifier = modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .heightIn(min = 48.dp)
        .padding(vertical = 8.dp, horizontal = 16.dp)
) {
    PrimaryText(
        text = startText,
        textAlpha = ContentAlphaManager.medium,
        modifier = Modifier.align(Alignment.CenterStart)
    )
    endSideSlot()
}

@Composable
fun DetailsItem2( //fixme: fix it!
    modifier: Modifier = Modifier,
    startText: String,
    onClick: () -> Unit,
    endSideSlot: @Composable () -> Unit
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .heightIn(min = 48.dp)
        .padding(vertical = 8.dp, horizontal = 16.dp)
) {
    PrimaryText(text = startText, textAlpha = ContentAlphaManager.medium)
    endSideSlot()
}

@Composable
fun DetailsItem(
    modifier: Modifier = Modifier, startText: String, endText: String, onClick: () -> Unit
) = DetailsItem(modifier = modifier, startText = startText, onClick = onClick) {
    PrimaryText(
        text = endText.ifEmpty { stringResource(R.string.all_tap_to_set) },
        color = if (endText.isEmpty()) ColorManager.primary else null,
    )
}

@Composable
fun DetailsItem(
    modifier: Modifier = Modifier, startText: String, color: Color, onClick: () -> Unit
) = DetailsItem(modifier = modifier, startText = startText, onClick = onClick) {
    if (color == Color.Transparent) PrimaryText(
        stringResource(R.string.all_tap_to_set), color = ColorManager.primary
    ) else ColorSample(color)
}

@Composable
fun DetailsItem(
    modifier: Modifier = Modifier,
    startText: String,
    selected: Boolean,
    onSelectionChange: (Boolean) -> Unit
) = DetailsItem(
    modifier = modifier,
    startText = startText,
    onClick = { onSelectionChange(!selected) }) {
    SelectableHexagonButton(selected = selected, onSelectionChange = { onSelectionChange(it) })
}
