package org.autorepair.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import org.autorepair.MR

object AddCarScreen : Screen {
    @Composable
    override fun Content() {
        AddCarScreenContent()
    }
}

@Composable
fun Screen.AddCarScreenContent(
) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(15.dp)
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(135.dp))
            AddCarImage()
            AddCarHeader(modifier = Modifier.padding(bottom = 25.dp))
            Dropdown("Year")
            Spacer(modifier = Modifier.height(7.dp))
            Dropdown("Make")
            Spacer(modifier = Modifier.height(7.dp))
            Dropdown("Model")
            Spacer(modifier = Modifier.height(7.dp))
            Dropdown("Engine")
            AddCarButton()
        }
    }
}

@Composable
fun AddCarImage(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.height(150.dp).width(205.dp)
    ) {
        Image(
            painter = painterResource(MR.images.ic_car),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun AddCarHeader(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier,
            fontSize = 25.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            fontFamily = fontFamilyResource(MR.fonts.Montserrat.medium),
            text = stringResource(MR.strings.add_your_car),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Dropdown(label: String) {
    val options = listOf("Honda", "Toyota", "BMW", "Tesla", "Dodge")
    val expanded = remember { mutableStateOf(false) }
//    val selectedOptionText = remember { mutableStateOf(options[0]) }
    val selectedOptionText = remember { mutableStateOf(label) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val primary = MaterialTheme.colorScheme.primary
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val onBackground = MaterialTheme.colorScheme.onBackground

    val dp1 = 1.dp
    val dp2 = 2.dp

    var color by remember { mutableStateOf(onPrimary) }
    var textColor by remember { mutableStateOf(onPrimary) }
    var stroke by remember { mutableStateOf(dp1) }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(BorderStroke(stroke, color), RoundedCornerShape(4.dp))
            .onGloballyPositioned { coordinates ->
                //This value is used to assign to the DropDown the same width
                textfieldSize = coordinates.size.toSize()
            }
            .clickable {
                expanded.value = !expanded.value
                color = primary
                stroke = dp2
            },
    ) {
        Text(
            text = selectedOptionText.value,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 15.dp),
            color = textColor
        )
        Icon(
            Icons.Filled.ArrowDropDown, "contentDescription",
            Modifier.align(Alignment.CenterEnd).padding(end = 15.dp),
            tint = color
        )
        DropdownMenu(
            expanded = expanded.value,
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() }),
            onDismissRequest = {
                expanded.value = false
                color = onPrimary
                textColor = if (selectedOptionText.value != label) onBackground else onPrimary
                stroke = dp1
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText.value = selectionOption
                        expanded.value = false
                        color = onPrimary
                        textColor =
                            if (selectedOptionText.value != label) onBackground else onPrimary
                        stroke = dp1
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

@Composable
fun AddCarButton() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            onClick = {},
            shape = RoundedCornerShape(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                androidx.compose.material.Text(
                    text = stringResource(MR.strings.add_car),
                    color = MaterialTheme.colorScheme.background,
                    style = TextStyle(
                        fontSize = 14.sp,
                    )
                )
            }
        }
    }
}