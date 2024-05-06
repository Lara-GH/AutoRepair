package org.autorepair.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.autorepair.ui.navigationbar.AutoRepairTab

object UserTabScreen: Screen {
    @Composable
    override fun Content() {
        UserTabContent()
    }
}

@OptIn(ExperimentalVoyagerApi::class)
@Composable
fun Screen.UserTabContent() {
    println("UserTabScreen $this!!!!!!!!!!!!!")
    TabNavigator(
        AutoRepairTab.HomeTab,
        tabDisposable = {
            TabDisposable(
                navigator = it,
                tabs = listOf(
                    AutoRepairTab.HomeTab,
                    AutoRepairTab.CarsTab,
                    AutoRepairTab.ChatTab,
                    AutoRepairTab.SettingsTab,
                    AutoRepairTab.BodyShopTab
                )
            )
        }
    ) {
        Scaffold(
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding),
                ) {
                    CurrentTab()
                }
            },
            bottomBar = {
                BottomNavigation(
                    backgroundColor = MaterialTheme.colorScheme.background,
                ) {
                    TabNavigationItem(AutoRepairTab.HomeTab)
                    TabNavigationItem(AutoRepairTab.CarsTab)
                    TabNavigationItem(AutoRepairTab.ChatTab)
                    TabNavigationItem(AutoRepairTab.SettingsTab)
                    TabNavigationItem(AutoRepairTab.BodyShopTab)
                }
            },
        )
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current.options.title == tab.options.title

    BottomNavigationItem(
        label = {
            tab.options.title.let {
                androidx.compose.material3.Text(
                    text = tab.options.title,
                    style = TextStyle(fontSize = 10.sp),
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    }
                )
            }
        },
        modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let {
                androidx.compose.material3.Icon(
                    painter = it,
                    contentDescription = tab.options.title,
                    tint = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    }
                )
            }
        }
    )
}