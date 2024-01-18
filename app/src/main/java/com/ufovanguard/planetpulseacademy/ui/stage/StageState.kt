package com.ufovanguard.planetpulseacademy.ui.stage

import android.os.Parcelable
import com.ufovanguard.planetpulseacademy.data.model.Stage
import kotlinx.parcelize.Parcelize

@Parcelize
data class StageState(
	val stage: Stage? = null
): Parcelable
