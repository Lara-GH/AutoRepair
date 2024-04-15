package org.autorepair.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import org.autorepair.MR
import org.autorepair.presentation.home.HomeEvent
import org.autorepair.presentation.home.HomeScreenModel
import org.autorepair.ui.navigationbar.AutoRepairTab

@Composable
fun Screen.HomeContent(){
    val parentNavigator = LocalNavigator.currentOrThrow.parent ?: error("No parent navigator")
    val tabNavigator = LocalTabNavigator.current
    val screenModel = getScreenModel<HomeScreenModel>()
    val state by screenModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            label = stringResource(MR.strings.book_a_service),
            painter = painterResource(MR.images.repair_tool),
            onClick = screenModel::onBookServiceClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            label = stringResource(MR.strings.add_a_car),
            painter = painterResource(MR.images.car4),
            onClick = screenModel::onAddCarClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            label = stringResource(MR.strings.contact_to_bodyshop),
            painter = painterResource(MR.images.chat),
            onClick = screenModel::onContactToBodyshopClick
        )
    }

    LaunchedEffect(true) {
        screenModel.events.collect { event ->
            when (event) {
                is HomeEvent.NavigateToBookService -> parentNavigator.push(AddCarScreen)
                is HomeEvent.NavigateToAddCar -> parentNavigator.push(AddCarScreen)
                is HomeEvent.NavigateToChat -> tabNavigator.current = AutoRepairTab.ChatTab()
            }
        }
    }
}
@Composable
fun Button(label: String,  painter: Painter, onClick: () -> Unit){
    androidx.compose.material3.Button(
        modifier = Modifier.fillMaxWidth().height(100.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp, pressedElevation = 0.dp),
        shape = RoundedCornerShape(5.dp),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Image(
                painter,
                contentDescription = "description",
                modifier = Modifier
                    .size(40.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onBackground,
                style = TextStyle(
                    fontFamily = fontFamilyResource(MR.fonts.Montserrat.semiBold),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                )
            )

            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "description",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}