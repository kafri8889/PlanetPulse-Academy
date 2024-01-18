package com.ufovanguard.planetpulseacademy.ui.lesson

import android.widget.Toast
import com.ufovanguard.planetpulseacademy.foundation.base.ui.UiEvent

object LessonUiEvent {

	data class ShowToast(
		override val message: String,
		override val length: Int = Toast.LENGTH_SHORT
	): UiEvent.ShowToast(message, length)

}