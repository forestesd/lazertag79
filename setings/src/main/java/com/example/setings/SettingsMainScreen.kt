package com.example.setings

import android.content.ClipData.Item
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsMainScreen(
    settingsViewModel: SettingsViewModel
) {

    val tagger by settingsViewModel.tagger.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                SettingsSlider(
                    onChange = settingsViewModel::setDamageIndex,
                    label = "Damage Index",
                    valueRange = (0f..100f),
                    defaultValue = tagger?.damageIndex ?: 0
                )
            }
            item {
                SettingsSlider(
                    onChange = settingsViewModel::setReloadTime,
                    label = "Reload Time seconds",
                    valueRange = (0f..30f),
                    defaultValue = tagger?.reloadTime ?: 0,
                )
            }
            item {
                SettingsSlider(
                    onChange = settingsViewModel::setShockTime,
                    label = "Shock Time seconds",
                    valueRange = (0f..30f),
                    defaultValue = tagger?.shockTime ?: 0
                )
            }
            item {
                SettingsSlider(
                    onChange = settingsViewModel::setInvulnerabilityTime,
                    label = "Invulnerability Time seconds",
                    valueRange = (0f..30f),
                    defaultValue = tagger?.invulnerabilityTime ?: 0,
                )
            }
            item {
                SettingsSlider(
                    onChange = settingsViewModel::setFireSpeed,
                    label = "Fire Speed",
                    valueRange = (0f..1200f),
                    defaultValue = tagger?.fireSpeed ?: 0,
                )
            }
            item {
                SettingsSlider(
                    onChange = settingsViewModel::setFirePower,
                    label = "Fire Power",
                    valueRange = (0f..10f),
                    defaultValue = tagger?.firePower ?: 0,
                )
            }
            item {
                SettingsSlider(
                    onChange = settingsViewModel::setVolume,
                    label = "Volume",
                    valueRange = (0f..10f),
                    defaultValue = tagger?.volume ?: 0
                )
            }

            item {
                SettingsSlider(
                    onChange = settingsViewModel::setReloadTime,
                    label = "Reload Time",
                    valueRange = (0f..100f),
                    defaultValue = tagger?.reloadTime ?: 0
                )
            }
            item {
                SettingsTextField(
                    onChange = settingsViewModel::setMaxHealth,
                    label = "Max health",
                    defaultValue = tagger?.maxHealth ?: 0
                )
            }
            item {
                SettingsTextField(
                    onChange = settingsViewModel::setMaxPatrons,
                    label = "Max patrons in shop",
                    defaultValue = tagger?.maxPatrons ?: 0
                )
            }

            item {
                SettingsTextField(
                    onChange = settingsViewModel::setMaxPatronsForGame,
                    label = "Max patrons for game",
                    defaultValue = tagger?.maxPatronsForGame ?: 0
                )
            }

            item {
                SettingsSwitch(
                    onCheck = settingsViewModel::toggleAutoReload,
                    label = "Auto reload",
                    defaultValue = tagger?.isAutoReload ?: false
                )
            }
            item {
                SettingsSwitch(
                    onCheck = settingsViewModel::toggleFriendlyFire,
                    label = "Friendly fire",
                    defaultValue = tagger?.isFriendlyFire ?: false
                )
            }

        }
        SplitButtonLayout(
            leadingButton = {
                SplitButtonDefaults.LeadingButton(onClick = { settingsViewModel.sendConfig() }) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Done Button"
                    )
                }
            },
            trailingButton = {
                SplitButtonDefaults.TrailingButton(onClick = { settingsViewModel.reset() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Done Button"
                    )
                }
            },
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomEnd),
        )
    }
}

@Composable
fun SettingsSlider(
    onChange: (Int) -> Unit,
    label: String,
    valueRange: ClosedFloatingPointRange<Float>,
    defaultValue: Int

) {

    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.9f) }

    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300)
        )
    }

    var value by remember(defaultValue) { mutableFloatStateOf(defaultValue.toFloat()) }

    LaunchedEffect(defaultValue) {
        value = defaultValue.toFloat()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .alpha(alpha.value)
            .scale(scale.value),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$label: ${value.toInt()}",
            fontSize = 24.sp
        )
        Slider(
            value = value,
            onValueChange = {
                value = it
            },
            modifier = Modifier.fillMaxWidth(),
            valueRange = valueRange,
            onValueChangeFinished = {
                onChange(value.toInt())
            }
        )
    }
}

@Composable
fun SettingsTextField(
    onChange: (Int) -> Unit,
    label: String,
    defaultValue: Int
) {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.9f) }

    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300)
        )
    }

    var value by remember(defaultValue) { mutableStateOf("$defaultValue") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .alpha(alpha.value)
            .scale(scale.value),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = label,
            fontSize = 24.sp
        )

        OutlinedTextField(
            value = value,
            onValueChange = {
                value = it
            },
            label = {
                Text(
                    text = label
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
            singleLine = true
        )
    }
    LaunchedEffect(value) {
        if (value != "") {
            onChange(value.toInt())
        }
    }
}

@Composable
fun SettingsSwitch(
    onCheck: () -> Unit,
    label: String,
    defaultValue: Boolean
) {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.9f) }

    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .alpha(alpha.value)
            .scale(scale.value),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = label,
            fontSize = 24.sp
        )
        Switch(
            checked = defaultValue,
            onCheckedChange = { onCheck() },
        )
    }
}