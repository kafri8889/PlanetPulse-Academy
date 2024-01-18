package com.ufovanguard.planetpulseacademy.ui.home

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptionsBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.data.Destination
import com.ufovanguard.planetpulseacademy.data.Destinations
import com.ufovanguard.planetpulseacademy.data.datasource.local.LocalAcademyDataProvider
import com.ufovanguard.planetpulseacademy.data.datasource.local.LocalLessonCategoryDataProvider
import com.ufovanguard.planetpulseacademy.data.model.Academy
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.common.LocalBottomBarPadding
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme
import com.ufovanguard.planetpulseacademy.foundation.uicomponent.AnimatedTextByChar
import com.ufovanguard.planetpulseacademy.foundation.uicomponent.CircleProgress
import com.ufovanguard.planetpulseacademy.foundation.uicomponent.LessonCategoryBox
import com.ufovanguard.planetpulseacademy.foundation.uicomponent.Measurer

@Preview
@Composable
private fun HomeScreenPreview() {
	PlanetPulseAcademyTheme {
		HomeScreenContent(
			state = HomeState(
				academies = LocalAcademyDataProvider.values
			),
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
		)
	}
}

@Preview
@Composable
private fun AcademyItemPreview() {
	PlanetPulseAcademyTheme {
		AcademyItem(
			academy = LocalAcademyDataProvider.academy1,
			onClick = {},
			modifier = Modifier
				.fillMaxWidth()
				.background(PPATheme.colorScheme.background)
		)
	}
}

@Composable
fun HomeScreen(
	viewModel: HomeViewModel,
	navigateTo: (Destination, builder: (NavOptionsBuilder.() -> Unit)?) -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel
	) { scaffoldPadding ->
		HomeScreenContent(
			state = state,
			onRefresh = viewModel::refresh,
			navigateTo = navigateTo,
			onAcademyClick = { academy ->
				navigateTo(Destinations.Main.stage, null)
			},
			modifier = Modifier
				.background(PPATheme.colorScheme.background)
				.padding(scaffoldPadding)
				.fillMaxSize()
		)
	}

}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)
@Composable
private fun HomeScreenContent(
	state: HomeState,
	modifier: Modifier = Modifier,
	onRefresh: () -> Unit = {},
	onAcademyClick: (Academy) -> Unit = {},
	navigateTo: (Destination, builder: (NavOptionsBuilder.() -> Unit)?) -> Unit = { _, _ ->}
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
			verticalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.matchParentSize()
		) {
			item {
				Row(
					verticalAlignment = Alignment.CenterVertically,
					modifier = Modifier
						.fillMaxWidth()
				) {
					Text(
						text = stringResource(id = R.string.hi_name, state.userCredential?.name ?: ""),
						style = PPATheme.typography.titleLarge,
						modifier = Modifier
							.padding(start = 16.dp)
							.weight(1f)
					)

					GlideImage(
						// TODO: Change with user profile image
						model = "https://image.tmdb.org/t/p/w235_and_h235_face/bzDpatBvlRXZQAqOdcKj5SDQmWg.jpg",
						contentDescription = null,
						contentScale = ContentScale.Crop,
						modifier = Modifier
							.padding(16.dp)
							.clip(CircleShape)
							.size(48.dp)
					)
				}
			}

			item {
				Card(
					colors = CardDefaults.cardColors(
						containerColor = Color(0xff7F4799)
					),
					modifier = Modifier
						.fillMaxWidth(0.92f)
				) {
					Column(
						verticalArrangement = Arrangement.spacedBy(8.dp),
						modifier = Modifier
							.padding(16.dp)
							.fillMaxWidth()
					) {
						Text(
							text = stringResource(id = R.string.academy_progress),
							style = PPATheme.typography.titleLarge
						)

						Measurer(
							contentToMeasure = {
								val longestName = remember {
									LocalLessonCategoryDataProvider.values.maxBy { it.name.length }
								}

								LessonCategoryBox(name = longestName.name)
							}
						) { dpSize ->
							Column(
								verticalArrangement = Arrangement.spacedBy(8.dp)
							) {
								for (category in LocalLessonCategoryDataProvider.values) {
									CategoryProgress(
										progress = 0.0f,
										category = category.name,
										boxWidth = dpSize.width
									)
								}
							}
						}
					}
				}
			}

//		item {
//			BoxWithConstraints(
//				modifier = Modifier
//					.fillMaxWidth(0.92f)
//			) {
//				LazyRow(
//					verticalAlignment = Alignment.CenterVertically,
//					horizontalArrangement = Arrangement.spacedBy(8.dp),
//					modifier = Modifier
//						.fillMaxWidth()
//				) {
//					items(4) {
//						Card(
//							modifier = Modifier
//								.width(this@BoxWithConstraints.maxWidth / 1.4f)
//								.height(96.dp)
//						) {
//
//						}
//					}
//				}
//			}
//		}

			item {
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(8.dp),
					modifier = Modifier
						.fillMaxWidth(0.92f)
				) {
					Text(
						text = stringResource(id = R.string.lessons),
						style = PPATheme.typography.titleLarge,
						modifier = Modifier
							.weight(1f)
					)

					TextButton(
						shape = MaterialTheme.shapes.small,
						contentPadding = ButtonDefaults.TextButtonWithIconContentPadding,
						colors = ButtonDefaults.textButtonColors(
							contentColor = PPATheme.colorScheme.onBackground
						),
						onClick = {
							navigateTo(Destinations.Main.lesson, null)
						}
					) {
						Text(text = stringResource(id = R.string.discover))

						Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

						Icon(
							painter = painterResource(id = R.drawable.ic_arrow_right_new),
							contentDescription = null,
							tint = PPATheme.colorScheme.onBackground,
							modifier = Modifier
								.size(ButtonDefaults.IconSize)
						)
					}
				}
			}

			items(
				items = state.academies,
				key = { it.id }
			) { academy ->
				AcademyItem(
					academy = academy,
					onClick = {
						onAcademyClick(academy)
					},
					modifier = Modifier
						.fillMaxWidth(0.92f)
				)
			}

			item {
				Spacer(
					modifier = Modifier
						.height(16.dp + LocalBottomBarPadding.current)
				)
			}
		}
	}


}

@Composable
private fun CategoryProgress(
	@FloatRange(0.0, 1.0) progress: Float,
	category: String,
	modifier: Modifier = Modifier,
	boxWidth: Dp = Dp.Unspecified
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		modifier = modifier
	) {
		LessonCategoryBox(
			name = category,
			modifier = if (boxWidth.isUnspecified) Modifier
			else Modifier.width(boxWidth)
		)

		BoxWithConstraints(
			modifier = Modifier.weight(1f)
				.height(10.dp)
				.clip(CircleShape)
				.background(MaterialTheme.colorScheme.surfaceVariant)
		) {
			Box(
				modifier = Modifier
					.width(maxWidth * progress)
					.height(maxHeight)
					.background(Color(0xffDEFF12))
			)
		}

		AnimatedTextByChar(
			text = "${progress * 100}%",
			style = PPATheme.typography.bodySmall
		)
	}
}

@Composable
private fun AcademyItem(
	academy: Academy,
	modifier: Modifier = Modifier,
	shape: Shape = MaterialTheme.shapes.medium,
	onClick: () -> Unit
) {
	Card(
		shape = shape,
		onClick = onClick,
		modifier = modifier,
		colors = CardDefaults.cardColors(
			containerColor = Color(0xff268A66)
		)
	) {
		Column(
			verticalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.padding(8.dp)
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Text(
					text = academy.title,
					style = PPATheme.typography.titleMedium,
					modifier = Modifier
						.weight(1f)
				)

				academy.categories.getOrNull(0)?.let { category ->
					LessonCategoryBox(name = category)
				}
			}

			Row(
				verticalAlignment = Alignment.Top,
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Text(
					text = academy.description,
					style = PPATheme.typography.bodyMedium,
					maxLines = 5,
					modifier = Modifier
						.weight(1f)
				)

				CircleProgress(
					progressAngle = { 0f },
					centerContent = {
						Row {
							AnimatedTextByChar(
								text = "0",
								style = PPATheme.typography.bodyMedium
							)

							Text(
								text = "%",
								style = PPATheme.typography.bodyMedium
							)
						}
					},
					modifier = Modifier
						.size(96.dp)
				)
			}
		}
	}
}
