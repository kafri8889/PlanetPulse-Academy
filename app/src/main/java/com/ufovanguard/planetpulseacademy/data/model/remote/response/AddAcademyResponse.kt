package com.ufovanguard.planetpulseacademy.data.model.remote.response

data class AddAcademyResponse(
	override val status: String,
	override val message: String
): CommonSingleResponse
