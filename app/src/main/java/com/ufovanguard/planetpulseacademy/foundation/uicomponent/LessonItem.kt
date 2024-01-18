package com.ufovanguard.planetpulseacademy.foundation.uicomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ufovanguard.planetpulseacademy.data.model.Lesson
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme

@Preview
@Composable
private fun LessonItemPreview() {
	PlanetPulseAcademyTheme {
		CoreLessonItem(
			image = {
				Box(
					modifier = Modifier
						.matchParentSize()
						.clip(MaterialTheme.shapes.small)
						.background(Color.LightGray)
				)
			},
			title = {
				Text("Life Below Water")
			},
			description = { maxLines ->
				Text(
					text = "Pada materi kali ini, kita akan berkenalanan tentang berbagai jenis lingkungan hidup yang ada di sekitar kita. " +
						"Selain itu, teman-teman juga akan dijelaskan tentang fungsi dari adanya lingkungan hidup. " +
						"Tapi sebelumnya mari kenali dulu pengertian dari lingkungan hidup, yuk!",
					maxLines = maxLines,
					overflow = TextOverflow.Ellipsis
				)
			}
		)
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LessonItem(
	lesson: Lesson,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {

	CoreLessonItem(
		modifier = modifier,
		image = {
			GlideImage(
				model = lesson.photoUrl,
				contentDescription = null,
				contentScale = ContentScale.Crop,
				modifier = Modifier
					.matchParentSize()
					.clip(MaterialTheme.shapes.small)
			)

			lesson.categories.getOrNull(0)?.let { category ->
				Box(
					contentAlignment = Alignment.Center,
					modifier = Modifier
						.padding(8.dp)
						.clip(MaterialTheme.shapes.small)
						.background(PPATheme.colorScheme.primary)
				) {
					Text(
						text = category.name,
						style = PPATheme.typography.labelSmall.copy(
							color = PPATheme.colorScheme.onPrimary
						),
						modifier = Modifier
							.padding(
								vertical = 4.dp,
								horizontal = 8.dp
							)
					)
				}
			}
		},
		title = {
			Text(lesson.title)
		},
		description = { maxLines ->
			Text(
				text = lesson.description,
				maxLines = maxLines,
				overflow = TextOverflow.Ellipsis
			)
		},
		trailingContent = {
			FilledIconButton(onClick = onClick) {
				Icon(
					imageVector = Icons.Rounded.Add,
					contentDescription = null
				)
			}
		}
	)
}

@Composable
private fun CoreLessonItem(
	modifier: Modifier = Modifier,
	image: @Composable BoxScope.() -> Unit,
	title: @Composable () -> Unit,
	description: @Composable (maxLines: Int) -> Unit,
	trailingContent: @Composable () -> Unit = {}
) {

	Row(
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		modifier = modifier
	) {
		Box(
			content = image,
			modifier = Modifier
				.weight(0.5f)
				.aspectRatio(2f / 1.4f)
		)

		Column(
			verticalArrangement = Arrangement.spacedBy(4.dp),
			modifier = Modifier
				.weight(0.6f)
		) {
			ProvideTextStyle(
				value = PPATheme.typography.titleMedium,
				content = title
			)

			ProvideTextStyle(PPATheme.typography.bodyMedium) {
				description(DESCRIPTION_MAX_LINES)
			}
		}

		trailingContent()
	}
}

private const val DESCRIPTION_MAX_LINES = 3