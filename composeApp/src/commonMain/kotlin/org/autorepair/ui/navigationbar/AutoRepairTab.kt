package org.autorepair.ui.navigationbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.icerock.moko.resources.compose.painterResource
import org.autorepair.MR
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

sealed class AutoRepairTab {

    internal class HomeTab : Tab {
        override val options: TabOptions
            @Composable
            get() = createTabOptions("Home", painterResource(MR.images.home), 0u)

        @Composable
        override fun Content() {
            HomeContent()
        }
    }

    internal class CarsTab : Tab {
        override val options: TabOptions
            @Composable
            get() = createTabOptions("Cars", painterResource(MR.images.car4), 1u)

        @Composable
        override fun Content() {
            CarsContent()
        }
    }

    internal class ChatTab : Tab {
        override val options: TabOptions
            @Composable
            get() = createTabOptions("Chat", painterResource(MR.images.chat), 2u)

        @Composable
        override fun Content() {
            ChatContent()
        }
    }

    internal class SettingsTab : Tab {
        override val options: TabOptions
            @Composable
            get() = createTabOptions("Settings", painterResource(MR.images.settings), 3u)

        @Composable
        override fun Content() {
            SettingsContent()
        }
    }

    internal class BodyShopTab : Tab {
        override val options: TabOptions
            @Composable
            get() = createTabOptions("BodyShop", painterResource(MR.images.bodyshop), 4u)

        @Composable
        override fun Content() {
            BodyShopContent()
        }
    }
}
