package com.example.navigation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomAppBar(
    onClick: (Int) -> Unit
) {
    val steps = listOf("Стартовая", "Игра", "Статистика", "Настройки")
    var currentStep by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp, horizontal = 50.dp)
            .navigationBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        steps.forEachIndexed { index, title ->
            HorizontalDivider(
                modifier = Modifier
                    .weight(if (index == 0) 0.9f else 0.1f)
                    .height(2.dp)
                    .background(Color.White)
                    .align(Alignment.CenterVertically)
            )
            StepItem(
                title = title,
                isSelected = index == currentStep,
                onClick = {
                    currentStep = index
                    onClick(currentStep)
                },
                modifier = Modifier.weight(1f)
            )

            HorizontalDivider(
                modifier = Modifier
                    .weight(if (index == steps.lastIndex) 0.9f else 0.1f)
                    .height(2.dp)
                    .background(Color.White)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun StepItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier

) {
    val backgroundColor = if (isSelected) Color.Red else Color.Transparent

    Box(
        modifier = modifier
            .clip(hexButton(0.05f))
            .border(1.dp, Color.White, hexButton(0.05f))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun hexButton(cutRatio: Float = 0.2f) = GenericShape { size, _ ->
    val cutX = size.width * cutRatio

    moveTo(cutX, 0f)
    lineTo(size.width - cutX, 0f)
    lineTo(size.width, size.height / 2)
    lineTo(size.width - cutX, size.height)
    lineTo(cutX, size.height)
    lineTo(0f, size.height / 2)
    close()
}

