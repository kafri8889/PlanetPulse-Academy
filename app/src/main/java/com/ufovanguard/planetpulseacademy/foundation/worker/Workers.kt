package com.ufovanguard.planetpulseacademy.foundation.worker

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.workDataOf
import com.ufovanguard.planetpulseacademy.data.model.remote.body.AddAcademyBody
import com.ufovanguard.planetpulseacademy.data.model.remote.body.ForgotPasswordBody
import com.ufovanguard.planetpulseacademy.data.model.remote.body.LoginBody
import com.ufovanguard.planetpulseacademy.data.model.remote.body.RegisterBody
import com.ufovanguard.planetpulseacademy.data.model.remote.body.ResetPasswordBody
import com.ufovanguard.planetpulseacademy.data.model.remote.body.VerifyOtpBody
import com.ufovanguard.planetpulseacademy.foundation.extension.toJson

object Workers {

	fun loginWorker(loginBody: LoginBody): OneTimeWorkRequest {
		return OneTimeWorkRequestBuilder<LoginWorker>()
			.setConstraints(
				Constraints(
					requiredNetworkType = NetworkType.CONNECTED
				)
			)
			.setInputData(
				workDataOf(
					LoginWorker.EXTRA_REQUEST_BODY to loginBody.toJson()
				)
			)
			.build()
	}

	fun registerWorker(registerBody: RegisterBody): OneTimeWorkRequest {
		return OneTimeWorkRequestBuilder<RegisterWorker>()
			.setConstraints(
				Constraints(
					requiredNetworkType = NetworkType.CONNECTED
				)
			)
			.setInputData(
				workDataOf(
					RegisterWorker.EXTRA_REQUEST_BODY to registerBody.toJson()
				)
			)
			.build()
	}

	fun forgotPasswordWorker(forgotPasswordBody: ForgotPasswordBody): OneTimeWorkRequest {
		return OneTimeWorkRequestBuilder<ForgotPasswordWorker>()
			.setConstraints(
				Constraints(
					requiredNetworkType = NetworkType.CONNECTED
				)
			)
			.setInputData(
				workDataOf(
					ForgotPasswordWorker.EXTRA_REQUEST_BODY to forgotPasswordBody.toJson()
				)
			)
			.build()
	}

	fun verifyOtp(email: String, verifyOtpBody: VerifyOtpBody): OneTimeWorkRequest {
		return OneTimeWorkRequestBuilder<VerifyOtpWorker>()
			.setConstraints(
				Constraints(
					requiredNetworkType = NetworkType.CONNECTED
				)
			)
			.setInputData(
				workDataOf(
					VerifyOtpWorker.EXTRA_EMAIL to email,
					VerifyOtpWorker.EXTRA_REQUEST_BODY to verifyOtpBody.toJson()
				)
			)
			.build()
	}

	fun resetPassword(code: String, resetPasswordBody: ResetPasswordBody): OneTimeWorkRequest {
		return OneTimeWorkRequestBuilder<ResetPasswordWorker>()
			.setConstraints(
				Constraints(
					requiredNetworkType = NetworkType.CONNECTED
				)
			)
			.setInputData(
				workDataOf(
					ResetPasswordWorker.EXTRA_CODE to code,
					ResetPasswordWorker.EXTRA_REQUEST_BODY to resetPasswordBody.toJson()
				)
			)
			.build()
	}

	fun profile(token: String): OneTimeWorkRequest {
		return OneTimeWorkRequestBuilder<ProfileWorker>()
			.setConstraints(
				Constraints(
					requiredNetworkType = NetworkType.CONNECTED
				)
			)
			.setInputData(
				workDataOf(
					ProfileWorker.EXTRA_TOKEN to token
				)
			)
			.build()
	}

	fun getAcademy(token: String): OneTimeWorkRequest {
		return OneTimeWorkRequestBuilder<GetAcademyWorker>()
			.setConstraints(
				Constraints(
					requiredNetworkType = NetworkType.CONNECTED
				)
			)
			.setInputData(
				workDataOf(
					GetAcademyWorker.EXTRA_TOKEN to token
				)
			)
			.build()
	}

	fun getAllLesson(token: String): OneTimeWorkRequest {
		return OneTimeWorkRequestBuilder<GetAllLessonWorker>()
			.setConstraints(
				Constraints(
					requiredNetworkType = NetworkType.CONNECTED
				)
			)
			.setInputData(
				workDataOf(
					GetAllLessonWorker.EXTRA_TOKEN to token
				)
			)
			.build()
	}

	fun addAcademy(token: String, body: AddAcademyBody): OneTimeWorkRequest {
		return OneTimeWorkRequestBuilder<AddAcademyWorker>()
			.setConstraints(
				Constraints(
					requiredNetworkType = NetworkType.CONNECTED
				)
			)
			.setInputData(
				workDataOf(
					AddAcademyWorker.EXTRA_TOKEN to token,
					AddAcademyWorker.EXTRA_REQUEST_BODY to body.toJson()
				)
			)
			.build()
	}

}