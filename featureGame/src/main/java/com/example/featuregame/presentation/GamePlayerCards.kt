package com.example.featuregame.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.models.TaggerInfoGameRes

@Composable
fun GamePlayerCard(
    taggerGame: TaggerInfoGameRes
){

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
                painter = painterResource(com.example.comon.R.drawable.img),
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
                    text = taggerGame.playerName ,
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

                }
            }
            Icon(
                painterResource(com.example.comon.R.drawable.gun),
                contentDescription = "Gun image",
                modifier = Modifier
                    .size(90.dp)
                    .scale(1.5f)
            )
        }
    }
}
}