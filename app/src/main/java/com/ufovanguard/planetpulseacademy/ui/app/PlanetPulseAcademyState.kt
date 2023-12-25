package com.ufovanguard.planetpulseacademy.ui.app

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.ufovanguard.planetpulseacademy.data.model.UserCredential
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class PlanetPulseAcademyState(
	val userCredential: UserCredential? = null,
): Parcelable
