package com.example.featuregame.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.models.TaggerInfoGame

@Composable
fun GamePlayerCard(
    taggerGame: TaggerInfoGame
) {
    LaunchedEffect (taggerGame) {
        Log.d("GAME", taggerGame.healthBarFill.toString())
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
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
                painter = painterResource(com.example.comon.R.drawable.img),
                contentDescription = "Player Icon",
                modifier = Modifier.size(80.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(taggerGame.healthBarFill)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Color(0xB74C5BF1)
                        )
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = taggerGame.taggerName,
                        fontSize = 16.sp
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom,
                            modifier =  Modifier.fillMaxWidth().weight(1f)
                        ) {
                            Icon(
                                painterResource(com.example.comon.R.drawable.ammo),
                                contentDescription = "Ammo Icon",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(
                                modifier = Modifier.width(5.dp)
                            )
                            Text(
                                text = taggerGame.patrons.toString(),
                                fontSize = 18.sp
                            )

                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().weight(1f)
                        ) {
                            Icon(
                                imageVector = (Icons.Filled.Favorite),
                                contentDescription = "Health Icon",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(
                                modifier = Modifier.width(10.dp)
                            )
                            Text(
                                text = taggerGame.health.toString(),
                                fontSize = 18.sp
                            )

                        }

                    }
                    Icon(
                        painterResource(com.example.comon.R.drawable.gun),
                        contentDescription = "Gun image",
                        modifier = Modifier
                            .size(90.dp)
                            .scale(1.5f)
                            .padding(end = 10.dp)
                    )
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f)
                    .padding(end = 20.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Убийств: ${taggerGame.kills}",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Смертей: ${taggerGame.deaths}",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "K/D: ${taggerGame.kills.toFloat() / taggerGame.deaths}",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
