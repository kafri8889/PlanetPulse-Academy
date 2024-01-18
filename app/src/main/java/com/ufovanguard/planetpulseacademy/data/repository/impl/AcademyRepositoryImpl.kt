package com.ufovanguard.planetpulseacademy.data.repository.impl

import com.ufovanguard.planetpulseacademy.data.datasource.local.dao.AcademyDao
import com.ufovanguard.planetpulseacademy.data.datasource.remote.AcademyService
import com.ufovanguard.planetpulseacademy.data.model.Academy
import com.ufovanguard.planetpulseacademy.data.model.remote.response.AcademyResponse
import com.ufovanguard.planetpulseacademy.data.model.remote.response.AddAcademyResponse
import com.ufovanguard.planetpulseacademy.data.repository.AcademyRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class AcademyRepositoryImpl @Inject constructor(
	private val academyService: AcademyService,
	private val academyDao: AcademyDao
): AcademyRepository {
	override suspend fun addRemoteAcademy(
		token: String,
		body: RequestBody
	): Response<AddAcademyResponse> {
		return academyService.addAcademy(token, body)
	}

	override suspend fun getRemoteAcademy(token: String): Response<AcademyResponse> {
		return academyService.getAcademy(token)
	}

	override fun getAllLocalAcademy(): Flow<List<Academy>> {
		return academyDao.getAll()
	}

	override fun getLocalAcademyById(id: String): Flow<Academy?> {
		return academyDao.getById(id)
	}

	override suspend fun insertLocalAcademy(vararg academy: Academy) {
		academyDao.insert(*academy)
	}

	override suspend fun insertLocalAcademy(academyList: Collection<Academy>) {
		academyDao.insert(academyList)
	}

	override suspend fun updateLocalAcademy(vararg academy: Academy) {
		academyDao.update(*academy)
	}

	override suspend fun updateLocalAcademy(academyList: Collection<Academy>) {
		academyDao.update(academyList)
	}

	override suspend fun deleteLocalAcademy(vararg academy: Academy) {
		academyDao.delete(*academy)
	}

	override suspend fun deleteLocalAcademy(academyList: Collection<Academy>) {
		academyDao.delete(academyList)
	}
}