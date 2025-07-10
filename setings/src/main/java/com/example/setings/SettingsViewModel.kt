package com.example.setings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comon.models.TaggerInfo
import com.example.comon.server.domain.useCases.TaggerConfigUpdateUseCase
import com.example.comon.server.domain.useCases.TaggersInfoUseCAse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val taggerConfigUpdateUseCase: TaggerConfigUpdateUseCase,
    taggerInfoUseCase: TaggersInfoUseCAse,
) : ViewModel() {

    private val taggersInfo: StateFlow<List<TaggerInfo>> = taggerInfoUseCase()

    private val _localTagger = MutableStateFlow<TaggerInfo?>(null)
    val tagger: StateFlow<TaggerInfo?> = _localTagger

    private val damageIndex: List<Int> =
        listOf(1, 2, 4, 5, 7, 10, 15, 17, 20, 25, 30, 35, 40, 50, 75, 100)

    init {
        viewModelScope.launch {
            taggersInfo.collect { list ->
                _localTagger.value = list.firstOrNull()
            }
        }
        tagger.value?.let { setIsAutoReload(it.isAutoReload) }
        tagger.value?.let { setIsFriendlyFire(it.isFriendlyFire) }
    }

    private fun update(update: (TaggerInfo) -> TaggerInfo) {
        _localTagger.update { it?.let(update) }
    }

    fun getDamageByIndex(index: Int): Int {
        Log.d("DAMAGE", "index: $index value: ${damageIndex[index]}")
        return damageIndex[index]
    }

    fun reset() {
        viewModelScope.launch {
            taggersInfo.collect { list ->
                _localTagger.value = list.firstOrNull()
            }
        }
        tagger.value?.let { setIsAutoReload(it.isAutoReload) }
        tagger.value?.let { setIsFriendlyFire(it.isFriendlyFire) }
    }

    fun setDamageIndex(value: Int) = update { it.copy(damageIndex = value) }
    fun setReloadTime(value: Int) = update { it.copy(reloadTime = value) }
    fun setShockTime(value: Int) = update { it.copy(shockTime = value) }
    fun setInvulnerabilityTime(value: Int) = update { it.copy(invulnerabilityTime = value) }
    fun setFireSpeed(value: Int) = update { it.copy(fireSpeed = value) }
    fun setFirePower(value: Int) = update { it.copy(firePower = value) }
    fun setMaxPatrons(value: Int) = update { it.copy(maxPatrons = value) }
    fun setMaxPatronsForGame(value: Int) = update { it.copy(maxPatronsForGame = value) }
    fun setMaxHealth(value: Int) = update { it.copy(maxHealth = value) }
    fun setIsAutoReload(value: Boolean) = update { it.copy(isAutoReload = value) }
    fun toggleAutoReload() = update { it.copy(isAutoReload = !it.isAutoReload) }
    fun setIsFriendlyFire(value: Boolean) = update { it.copy(isFriendlyFire = value) }
    fun toggleFriendlyFire() = update { it.copy(isFriendlyFire = !it.isFriendlyFire) }
    fun setFireMode(value: Int) = update { it.copy(fireMode = value) }
    fun setVolume(value: Int) = update { it.copy(volume = value) }

    fun sendConfig() {
        viewModelScope.launch {
            _localTagger.value?.let { tagger ->
                taggerConfigUpdateUseCase.invoke(listOf(tagger, taggersInfo.value[1]))
            }
        }
    }
}
