package com.ufovanguard.planetpulseacademy.ui.lesson

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptionsBuilder
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.data.Destination
import com.ufovanguard.planetpulseacademy.data.model.Lesson
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme
import com.ufovanguard.planetpulseacademy.foundation.uicomponent.LessonItem
import com.ufovanguard.planetpulseacademy.foundation.uicomponent.PPATextField

@Preview
@Composable
private fun LessonScreenPreview() {

	PlanetPulseAcademyTheme {
		LessonScreenContent(
			state = LessonState(),
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
		)
	}
}

@Composable
fun LessonScreen(
	viewModel: LessonViewModel,
	onNavigateUp: () -> Unit,
	navigateTo: (Destination, builder: (NavOptionsBuilder.() -> Unit)?) -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel
	) { scaffoldPadding ->
		LessonScreenContent(
			state = state,
			onSearchQueryChange = viewModel::setSearchQuery,
			onLessonClicked = viewModel::addLesson,
			onNavigateUp = onNavigateUp,
			onRefresh = viewModel::refresh,
			onSearch = viewModel::search,
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
				.padding(scaffoldPadding)
		)
	}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun LessonScreenContent(
	state: LessonState,
	modifier: Modifier = Modifier,
	onSearchQueryChange: (String) -> Unit = {},
	onLessonClicked: (Lesson) -> Unit = {},
	onNavigateUp: () -> Unit = {},
	onRefresh: () -> Unit = {},
	onSearch: () -> Unit = {}
) {

	val pullRefreshState = rememberPullRefreshState(
		refreshing = state.isRefreshing,
		onRefresh = onRefresh
	)

	Box(
		contentAlignment = Alignment.TopCenter,
		modifier = modifier
			.pullRefresh(pullRefreshState)
	) {
		PullRefreshIndicator(
			refreshing = state.isRefreshing,
			state = pullRefreshState,
			modifier = Modifier
				.statusBarsPadding()
				.zIndex(1f)
		)

		LazyColumn(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = if (state.lessons.isEmpty()) Arrangement.Center
			else Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.matchParentSize()
		) {

			// If lessons is empty, show empty UI
			if (state.lessons.isEmpty()) {
				item {
					Text(
						text = "Lesson tidak tersedia",
						textAlign = TextAlign.Center,
						style = PPATheme.typography.titleMedium,
						modifier = Modifier
							.fillMaxWidth(0.92f)
					)
				}
			} else {
				item {
					val searchTextFieldTrailingIcon: @Composable () -> Unit = {
						IconButton(
							onClick = {
								onSearchQueryChange("")
							}
						) {
							Icon(
								imageVector = Icons.Rounded.Clear,
								contentDescription = null
							)
						}
					}

					Spacer(modifier = Modifier.height(8.dp))

					PPATextField(
						value = state.searchQuery,
						onValueChange = onSearchQueryChange,
						containerColor = PPATheme.colorScheme.onBackground,
						singleLine = true,
						keyboardOptions = KeyboardOptions(
							imeAction = ImeAction.Search
						),
						keyboardActions = KeyboardActions(
							onSearch = {
								onSearch()
							}
						),
						placeholder = {
							Text(
								stringResource(id = R.string.search)
							)
						},
						leadingIcon = {
							Icon(
								painter = painterResource(id = R.drawable.ic_search),
								contentDescription = null
							)
						},
						trailingIcon = if (state.searchQuery.isNotBlank()) {
							{ searchTextFieldTrailingIcon() }
						} else null,
						modifier = Modifier
							.fillMaxWidth(0.92f)
							.statusBarsPadding()
					)
				}
			}

			item {
				Spacer(modifier = Modifier.height(8.dp))
			}

			items(
				items = state.lessons,
				key = { it.id }
			) { lesson ->
				LessonItem(
					lesson = lesson,
					onClick = {
						onLessonClicked(lesson)
					},
					modifier = Modifier
						.fillMaxWidth(0.92f)
				)
			}

			item {
				Spacer(modifier = Modifier.height(16.dp))
			}
		}
	}

}
