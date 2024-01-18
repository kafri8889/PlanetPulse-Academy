package com.ufovanguard.planetpulseacademy.ui.lesson

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ufovanguard.planetpulseacademy.data.model.Lesson
import com.ufovanguard.planetpulseacademy.data.model.remote.body.AddAcademyBody
import com.ufovanguard.planetpulseacademy.data.repository.LessonRepository
import com.ufovanguard.planetpulseacademy.data.repository.UserCredentialRepository
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import com.ufovanguard.planetpulseacademy.foundation.worker.LoginWorker
import com.ufovanguard.planetpulseacademy.foundation.worker.Workers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LessonViewModel @Inject constructor(
	private val userCredentialRepository: UserCredentialRepository,
	private val lessonRepository: LessonRepository,
	private val workManager: WorkManager,
	savedStateHandle: SavedStateHandle
): BaseViewModel<LessonState>(
	defaultState = LessonState(),
	savedStateHandle = savedStateHandle
) {

	private val _currentGetLessonWorkId = MutableStateFlow<UUID?>(null)
	private val currentGetLessonWorkId: StateFlow<UUID?> = _currentGetLessonWorkId

	private val _currentAddLessonWorkId = MutableStateFlow<UUID?>(null)
	private val currentAddLessonWorkId: StateFlow<UUID?> = _currentAddLessonWorkId

	init {
		viewModelScope.launch {
			currentGetLessonWorkId.filterNotNull().flatMapLatest { id ->
				workManager.getWorkInfoByIdFlow(id)
			}.collectLatest { workInfo ->
				updateState {
					when (workInfo.state) {
						WorkInfo.State.FAILED, WorkInfo.State.SUCCEEDED -> {
							copy(isRefreshing = false)
						}
						else -> this
					}
				}
			}
		}

		viewModelScope.launch {
			currentAddLessonWorkId.filterNotNull().flatMapLatest { id ->
				workManager.getWorkInfoByIdFlow(id)
			}.collectLatest { workInfo ->
				updateState {
					when (workInfo.state) {
						WorkInfo.State.FAILED -> {
							sendEvent(
								LessonUiEvent.ShowToast(
									workInfo.outputData.getString(LoginWorker.EXTRA_ERROR_MESSAGE)
										?: "Unknown error"
								)
							)
							this
						}
						WorkInfo.State.SUCCEEDED -> {
							sendEvent(LessonUiEvent.ShowToast("Lesson added"))

							state.value.userCredential?.let { cred ->
								Workers.getAcademy(cred.getBearerToken())
							}

							this
						}
						else -> this
					}
				}
			}
		}

		viewModelScope.launch {
			userCredentialRepository.getUserCredential.collectLatest { cred ->
				updateState {
					copy(
						userCredential = cred
					)
				}
			}
		}

		viewModelScope.launch {
			lessonRepository.getAllLocalLesson().collectLatest { lessonList ->
				updateState {
					copy(
						lessons = lessonList
					)
				}
			}
		}
	}

	fun setSearchQuery(q: String) {
		updateState {
			copy(
				searchQuery = q
			)
		}
	}

	fun addLesson(lesson: Lesson) {
		state.value.userCredential?.let { cred ->
			viewModelScope.launch {
				workManager.enqueue(
					Workers.addAcademy(
						token = cred.getBearerToken(),
						body = AddAcademyBody(lesson.id)
					).also {
						_currentAddLessonWorkId.emit(it.id)
					}
				)
			}
		}
	}

	fun refresh() {
		state.value.userCredential?.let { cred ->
			viewModelScope.launch {
				updateState {
					copy(
						isRefreshing = true
					)
				}

				workManager.enqueue(
					Workers.getAllLesson(cred.getBearerToken()).also {
						_currentGetLessonWorkId.emit(it.id)
					}
				)
			}
		}
	}

	fun search() {

	}

}