package com.ufovanguard.planetpulseacademy.ui.stage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptionsBuilder
import com.ufovanguard.planetpulseacademy.data.Destination
import com.ufovanguard.planetpulseacademy.data.Destinations
import com.ufovanguard.planetpulseacademy.data.datasource.local.LocalStageDataProvider
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.extension.Zero
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.uicomponent.QuizList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StageScreen(
	viewModel: StageViewModel,
	navigateTo: (Destination, builder: (NavOptionsBuilder.() -> Unit)?) -> Unit = { _, _ ->}
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel,
		contentWindowInsets = WindowInsets.Zero
	) { scaffoldPadding ->
		StageScreenContent(
			state = StageState(LocalStageDataProvider.stage1),
			navigateTo = navigateTo,
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.primaryContainer)
				.padding(scaffoldPadding)
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StageScreenContent(
	state: StageState,
	modifier: Modifier = Modifier,
	navigateTo: (Destination, builder: (NavOptionsBuilder.() -> Unit)?) -> Unit = { _, _ ->}
) {

	Column(
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = modifier
	) {
		TopAppBar(
			title = {
				Text(state.stage?.name ?: "")
			},
			colors = TopAppBarDefaults.topAppBarColors(
				containerColor = PPATheme.colorScheme.secondary,
				titleContentColor = PPATheme.colorScheme.onBackground
			)
		)

		QuizList(
			quizzes = state.stage?.quizzes ?: emptyList(),
			onClick = { quiz ->
				navigateTo(Destinations.Main.quiz, null)
			},
			modifier = Modifier
				.fillMaxSize()
		)
	}
}
