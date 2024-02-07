package org.autorepair.ui.navigationbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.autorepair.ui.BodyShopContent
import org.autorepair.ui.CarsContent
import org.autorepair.ui.ChatContent
import org.autorepair.ui.HomeContent
import org.autorepair.ui.SettingsContent

@Composable
private fun createTabOptions(title: String, icon: Painter, index: UShort): TabOptions {
    return TabOptions(
        index = index,
        title = title,
        icon = icon,
    )
}

internal sealed class AutoRepairTab {
    internal class HomeTab(painter: Painter) : Tab {
        private val _icon = painter
        override val options: TabOptions
            @Composable
            get() = createTabOptions("Home", _icon, 0u)

        @Composable
        override fun Content() {
            HomeContent()
        }
    }

    internal class CarsTab(painter: Painter) : Tab {
        private val _icon = painter
        override val options: TabOptions
            @Composable
            get() = createTabOptions("Cars", _icon, 1u)

        @Composable
        override fun Content() {
            CarsContent()
        }
    }

    internal class ChatTab(painter: Painter) : Tab {
        private val _icon = painter
        override val options: TabOptions
            @Composable
            get() = createTabOptions("Chat", _icon, 2u)

        @Composable
        override fun Content() {
            ChatContent()
        }
    }

    internal class SettingsTab(painter: Painter) : Tab {
        private val _icon = painter
        override val options: TabOptions
            @Composable
            get() = createTabOptions("Settings", _icon, 3u)

        @Composable
        override fun Content() {
            SettingsContent()
        }
    }

    internal class BodyShopTab(painter: Painter) : Tab {
        private val _icon = painter
        override val options: TabOptions
            @Composable
            get() = createTabOptions("BodyShop", _icon, 4u)

        @Composable
        override fun Content() {
            BodyShopContent()
        }
    }
}
