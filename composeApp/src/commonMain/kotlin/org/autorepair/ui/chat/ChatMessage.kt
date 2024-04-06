package org.autorepair.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatMessage(isMyMessage: Boolean, message: String, currentDateTime: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (isMyMessage) Alignment.CenterEnd else Alignment.CenterStart
    ) {

        Row(verticalAlignment = Alignment.Bottom) {
            if (!isMyMessage) {
                Column {
//                    UserPic(message.user)
                }
                Spacer(Modifier.size(2.dp))
                Column {
                    Triangle(true, MaterialTheme.colorScheme.secondary)
                }
            }

            Column {
                Box(
                    Modifier.clip(
                        RoundedCornerShape(
                            10.dp,
                            10.dp,
                            if (!isMyMessage) 10.dp else 0.dp,
                            if (!isMyMessage) 0.dp else 10.dp
                        )
                    )
                        .background(color = if (!isMyMessage) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primaryContainer)
                        .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp),
                ) {
                    Column {
                        if (!isMyMessage) {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = "BodyShop",
                                    style = MaterialTheme.typography.bodyMedium
                                        .copy(
                                            fontWeight = FontWeight.SemiBold,
                                            letterSpacing = 0.sp,
                                            fontSize = 14.sp
                                        ),
                                    color = MaterialTheme.colorScheme.background
                                )
                            }
                        }
                        Spacer(Modifier.size(3.dp))
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 18.sp,
                                letterSpacing = 0.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Spacer(Modifier.size(4.dp))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text(
                                text = currentDateTime,
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.titleMedium
                                    .copy(fontSize = 10.sp),
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                }
                Box(Modifier.size(10.dp))
            }
            if (isMyMessage) {
                Column {
                    Triangle(false, MaterialTheme.colorScheme.primaryContainer)
                }
            }
        }
    }
}