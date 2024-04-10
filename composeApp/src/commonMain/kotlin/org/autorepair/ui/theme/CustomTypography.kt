package org.autorepair.ui.theme

import androidx.compose.material.Typography
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.fontFamilyResource
import org.autorepair.MR

@Composable
fun CustomTypography(): Typography {

    return Typography(
        body1 = TextStyle(
            fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        body2 = TextStyle(
            fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.outline
        ),
        h1 = TextStyle(
            fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.outline,
            baselineShift = BaselineShift(-2f)
        )
    )
}