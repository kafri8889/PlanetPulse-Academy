package com.ufovanguard.planetpulseacademy.ui.quiz

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class QuizState(
	val any: String = ""
): Parcelable