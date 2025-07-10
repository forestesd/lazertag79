package com.example.mainscreen.presentation.taggerTeams

import androidx.compose.material3.DrawerValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ConnectedTaggerViewModel @Inject constructor() : ViewModel() {
    private val _drawerState = MutableStateFlow(DrawerValue.Closed)
    val drawerState: StateFlow<DrawerValue> = _drawerState.asStateFlow()

    fun openDrawer() {
        viewModelScope.launch {
            _drawerState.value = DrawerValue.Open
        }
    }

    fun closeDrawer() {
        viewModelScope.launch {
            _drawerState.value = DrawerValue.Closed
        }
    }
}