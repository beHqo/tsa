package com.example.android.strikingarts.ui.scaffold

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.android.strikingarts.domain.model.ImmutableList
import com.example.android.strikingarts.ui.model.BottomNavigationItem

@Composable
fun BottomNavigationBar(
    bottomNavigationItems: ImmutableList<BottomNavigationItem>,
    currentRoute: String,
    modifier: Modifier = Modifier
) = NavigationBar(modifier = modifier) {
    bottomNavigationItems.forEach { bottomNavigationItem ->
        NavigationBarItem(selected = currentRoute == bottomNavigationItem.route,
            onClick = bottomNavigationItem.onClick,
            label = { Text(bottomNavigationItem.screenName) },
            icon = { Icon(painterResource(bottomNavigationItem.iconId), null) })
    }
}
