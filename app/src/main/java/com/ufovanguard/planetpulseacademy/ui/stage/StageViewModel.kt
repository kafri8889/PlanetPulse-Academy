package com.ufovanguard.planetpulseacademy.ui.stage

import androidx.lifecycle.SavedStateHandle
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StageViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle
): BaseViewModel<StageState>(
	defaultState = StageState(),
	savedStateHandle = savedStateHandle
) {
}