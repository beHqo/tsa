package com.thestrikingarts.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.thestrikingarts.R
import com.thestrikingarts.ui.components.VerticalExpandAnimatedVisibility
import com.thestrikingarts.ui.model.BottomNavigationItem
import com.thestrikingarts.ui.navigation.NavGraph
import com.thestrikingarts.ui.navigation.Screen
import com.thestrikingarts.ui.navigation.navigateToCalendarScreen
import com.thestrikingarts.ui.navigation.navigateToComboScreen
import com.thestrikingarts.ui.navigation.navigateToHomeScreen
import com.thestrikingarts.ui.navigation.navigateToTechniqueScreen
import com.thestrikingarts.ui.navigation.navigateToWorkoutScreen
import com.thestrikingarts.ui.scaffold.BottomNavigationBar
import kotlinx.coroutines.launch

@Composable
fun StrikingArtsApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            screenName = stringResource(R.string.all_home),
            iconId = R.drawable.rounded_home_24,
            route = Screen.Home.route,
            onClick = navController::navigateToHomeScreen
        ), BottomNavigationItem(
            screenName = stringResource(R.string.all_technique),
            iconId = R.drawable.baseline_sports_martial_arts_24,
            route = Screen.Technique.route,
            onClick = navController::navigateToTechniqueScreen
        ), BottomNavigationItem(
            screenName = stringResource(R.string.all_workout),
            iconId = R.drawable.round_sports_mma_24,
            route = Screen.Workout.route,
            onClick = navController::navigateToWorkoutScreen
        ), BottomNavigationItem(
            screenName = stringResource(R.string.all_combo),
            iconId = R.drawable.combo_24,
            route = Screen.Combo.route,
            onClick = navController::navigateToComboScreen
        ), BottomNavigationItem(
            screenName = stringResource(R.string.all_calendar),
            iconId = R.drawable.rounded_calendar_month_24,
            route = Screen.Calendar.route,
            onClick = navController::navigateToCalendarScreen
        )
    )

    var selectionMode by rememberSaveable { mutableStateOf(false) }
    val setSelectionModeValueGlobally = { value: Boolean -> selectionMode = value }

    val bottomNavBarVisible by remember(currentRoute, selectionMode) {
        derivedStateOf { !selectionMode && bottomNavigationItems.any { currentRoute == it.route } }
    }

    val coroutineScope = rememberCoroutineScope()
    val snackbarState = remember { SnackbarHostState() }

    Scaffold(bottomBar = {
        VerticalExpandAnimatedVisibility(visible = bottomNavBarVisible) {
            BottomNavigationBar(
                bottomNavigationItems = bottomNavigationItems, currentRoute = currentRoute
            )
        }
    }, snackbarHost = { SnackbarHost(snackbarState) }) {
        NavGraph(
            navController = navController,
            setSelectionModeValueGlobally = setSelectionModeValueGlobally,
            showSnackbar = {
                coroutineScope.launch {
                    snackbarState.showSnackbar(message = it, withDismissAction = true)
                }
            },
            modifier = Modifier.padding(it)
        )
    }
}