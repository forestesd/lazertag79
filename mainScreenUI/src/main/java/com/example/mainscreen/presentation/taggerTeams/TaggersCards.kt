package com.example.mainscreen.presentation.taggerTeams

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.models.BatteryColor
import com.example.comon.models.TaggerInfo
import com.example.mainscreen.R

@Composable
fun ConnectedTaggerCard(tagger: TaggerInfo) {
    Card(
        modifier = Modifier
            .height(111.dp)
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
            pressedElevation = 5.dp
        )

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD6EAFE).copy(alpha = 0.9f)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Id: ${tagger.taggerId}",
            )
            Row {


                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        Text(
                            text = "Таггер:",
                            fontSize = 16.sp
                        )

                    }
                    Row {
                        Text(
                            text = "Повязка:",
                            fontSize = 16.sp
                        )

                    }

                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BatteryPercents(tagger)
                }
            }
        }
    }
}

@Composable
fun TeamPlayerCard(
    tagger: TaggerInfo,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
            pressedElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),

            ) {
            Image(
                painter = painterResource(R.drawable.img),
                contentDescription = "Player Icon",
                modifier = Modifier.size(80.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tagger.playerName ,
                    fontSize = 16.sp
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        Text(
                            text = "Таггер:",
                            fontSize = 16.sp
                        )

                    }
                    Row {
                        Text(
                            text = "Повязка:",
                            fontSize = 16.sp
                        )

                    }

                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BatteryPercents(tagger)
                }
            }
            Icon(
                painterResource(R.drawable.gun),
                contentDescription = "Gun image",
                modifier = Modifier
                    .size(90.dp)
                    .scale(1.5f)
            )
        }
    }
}

@Composable
fun BatteryPercents(
    tagger: TaggerInfo
) {

    Row {
        Text(
            text = "${tagger.taggerCharge}%",
            color = when (tagger.taggerChargeColor) {
                BatteryColor.Green -> Color(0xFF1EC70B)
                BatteryColor.Yellow -> Color(0xFFFFFB00)
                else -> Color(0xFFFF0000)
            }
        )
        BatteryIcon(
            color = when (tagger.taggerChargeColor) {
                BatteryColor.Green -> Color(0xFF1EC70B)
                BatteryColor.Yellow -> Color(0xFFFFFB00)
                else -> Color(0xFFFF0000)
            },
            fillFraction = ((tagger.taggerCharge) / 100f).coerceIn(0f, 1f)
        )
    }

    Row {
        Text(
            text = "${tagger.bandageCharge }%",
            color = when (tagger.bandageChargeColor) {
                BatteryColor.Green -> Color(0xFF1EC70B)
                BatteryColor.Yellow -> Color(0xFFFFFB00)
                else -> Color(0xFFFF0000)
            }
        )
        BatteryIcon(
            color = when (tagger.bandageChargeColor) {
                BatteryColor.Green -> Color(0xFF1EC70B)
                BatteryColor.Yellow -> Color(0xFFFFFB00)
                else -> Color(0xFFFF0000)
            },
            fillFraction = ((tagger.bandageCharge) / 100f).coerceIn(0f, 1f)
        )
    }


}

@Composable
fun BatteryIcon(
    color: Color,
    fillFraction: Float
) {
    Box(
        modifier = Modifier
            .height(25.dp)
            .width(30.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            painterResource(R.drawable.battery),
            contentDescription = "Battery Icon",
            tint = color,
            modifier = Modifier.fillMaxSize()
        )
        Icon(
            painterResource(R.drawable.battery_full),
            contentDescription = "Fill Battery Icon",
            tint = color,
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    val width = size.width
                    val clipLeft =
                        width * (1f - fillFraction)
                    clipRect(
                        left = clipLeft,
                        top = 0f,
                        right = size.width,
                        bottom = size.height
                    ) {
                        this@drawWithContent.drawContent()
                    }

                }
        )
    }
}