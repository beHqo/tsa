package com.example.android.strikingarts.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.strikingarts.R

@Composable
fun SingleLineItem(
    primaryText: String,
    onItemClick: () -> Unit,
    onMoreVertClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .heightIn(min = 48.dp)
            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
            .clickable { onItemClick() }
    ) {
        Text(
            text = primaryText,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 1,
            modifier = Modifier
                .weight(1F)
        )
        MoreVertIconButton(
            modifier = Modifier.size(24.dp), onMoreVertClick
        )
    }

}

@Composable
fun DoubleLineItem() {

}

@Composable
fun DoubleLineItemWithImage(
    primaryText: String,
    secondaryText: String,
    @DrawableRes image: Int,
    imageContentDescription: String,
    onItemClick: () -> Unit,
    onMoreVertClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .heightIn(min = 72.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onItemClick() }
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = imageContentDescription,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(end = 16.dp)
                .height(56.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1F)
        ) {
            Text(
                text = primaryText,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1
            )
            Text(
                text = secondaryText,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5F)
            )
        }
        MoreVertIconButton(
            modifier = Modifier.size(24.dp), onMoreVertClick
        )
    }
}

@Composable
fun TripleLineItem() {

}