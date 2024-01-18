package com.ufovanguard.planetpulseacademy.data.datasource.remote

import com.ufovanguard.planetpulseacademy.data.model.remote.response.AcademyResponse
import com.ufovanguard.planetpulseacademy.data.model.remote.response.AddAcademyResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AcademyService {

	@GET("/api/user/academy")
	suspend fun getAcademy(
		@Header("Authorization") token: String
	): Response<AcademyResponse>

	@POST("/api/user/academy/post")
	suspend fun addAcademy(
		@Header("Authorization") token: String,
		@Body body: RequestBody
	): Response<AddAcademyResponse>

}