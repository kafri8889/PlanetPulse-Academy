package com.ufovanguard.planetpulseacademy.data.datasource.local

import com.ufovanguard.planetpulseacademy.data.model.Stage

object LocalStageDataProvider {

	val stage1 = Stage(
		id = "65a2829aea66f69de3bf83c4",
		name = "Stage 1 - Lingkungan hidup",
		idLesson = "65a28279e95f2da9ec4bfd7d",
		difficulty = 1,
		createdAt = "2024-01-13T12:30:49.207Z",
		updatedAt = "2024-01-15T12:14:31.418Z",
		v = 0,
		quizzes = LocalQuizDataProvider.values
	)

	val values = listOf(
		stage1
	)

}