package com.thestrikingarts.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.thestrikingarts.R
import com.thestrikingarts.ui.model.Time
import com.thestrikingarts.ui.theme.designsystemmanager.ColorManager
import com.thestrikingarts.ui.theme.designsystemmanager.ContentAlphaManager
import com.thestrikingarts.ui.theme.designsystemmanager.PaddingManager
import com.thestrikingarts.ui.util.localized
import java.util.Locale

@Composable
private fun IntPicker(
    modifier: Modifier = Modifier,
    label: (Int) -> String = { it.toString() },
    value: Int,
    onValueChange: (Int) -> Unit,
    dividersColor: Color = ColorManager.primary,
    range: List<Int>,
    textStyle: TextStyle = LocalTextStyle.current,
) = ListItemPicker(
    modifier = modifier,
    label = label,
    value = value,
    onValueChange = onValueChange,
    dividersColor = dividersColor,
    list = range,
    textStyle = textStyle
)

@Composable
private fun BaseTimePicker(
    modifier: Modifier = Modifier,
    listPickerName: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    range: List<Int> = (0..59).toList(),
    dividersColor: Color = ColorManager.primary,
    textStyle: TextStyle = LocalTextStyle.current,
) = Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

    Text(listPickerName, modifier = Modifier.padding(end = PaddingManager.Small))

    IntPicker(
        label = { String.format(Locale.getDefault(), "%02d", it) },
        value = value,
        onValueChange = onValueChange,
        dividersColor = dividersColor,
        range = range,
        textStyle = textStyle
    )
}

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    value: Time,
    onValueChange: (Time) -> Unit,
    minutesRange: List<Int> = (0..59).toList(),
    secondsRange: List<Int> = (0..59).toList(),
    dividersColor: Color = ColorManager.primary,
    textStyle: TextStyle = LocalTextStyle.current,
) = CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        BaseTimePicker(
            listPickerName = stringResource(R.string.all_minutes),
            value = value.minutes,
            onValueChange = { onValueChange(value.copy(minutes = it)) },
            range = minutesRange,
            dividersColor = dividersColor,
            textStyle = textStyle
        )

        Text(
            text = ":",
            style = textStyle,
            modifier = Modifier
                .offset(y = 8.dp)
                .padding(horizontal = PaddingManager.Large)
        )

        BaseTimePicker(
            listPickerName = stringResource(R.string.all_seconds),
            value = value.seconds,
            onValueChange = { onValueChange(value.copy(seconds = it)) },
            range = secondsRange,
            dividersColor = dividersColor,
            textStyle = textStyle
        )
    }
}

@Composable
fun ColumnScope.IntPickerBottomSheet(
    modifier: Modifier = Modifier,
    range: List<Int>,
    isError: Boolean = false,
    helperText: String = "",
    errorText: String = "",
    quantity: Int,
    setQuantity: (Int) -> Unit
) {
    IntPicker(
        modifier = modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(),
        label = { it.localized() },
        value = quantity,
        onValueChange = setQuantity,
        range = range
    )

    SecondaryText(
        text = if (isError) errorText else helperText,
        color = if (isError) ColorManager.error
        else ColorManager.onSurface.copy(ContentAlphaManager.medium),
        modifier = Modifier
            .offset(y = PaddingManager.Large)
            .padding(horizontal = PaddingManager.Medium)
    )
}