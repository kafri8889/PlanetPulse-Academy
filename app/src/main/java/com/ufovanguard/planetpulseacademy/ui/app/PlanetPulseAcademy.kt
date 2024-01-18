package com.ufovanguard.planetpulseacademy.ui.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.ufovanguard.planetpulseacademy.data.Destination
import com.ufovanguard.planetpulseacademy.data.Destinations
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.common.LocalBottomBarPadding
import com.ufovanguard.planetpulseacademy.foundation.extension.Zero
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.ui.academy.AcademyScreen
import com.ufovanguard.planetpulseacademy.ui.forgot_password.ForgotPasswordScreen
import com.ufovanguard.planetpulseacademy.ui.home.HomeScreen
import com.ufovanguard.planetpulseacademy.ui.lesson.LessonScreen
import com.ufovanguard.planetpulseacademy.ui.login.LoginScreen
import com.ufovanguard.planetpulseacademy.ui.onboarding.OnboardingScreen
import com.ufovanguard.planetpulseacademy.ui.profile.ProfileScreen
import com.ufovanguard.planetpulseacademy.ui.quiz.QuizScreen
import com.ufovanguard.planetpulseacademy.ui.register.RegisterScreen
import com.ufovanguard.planetpulseacademy.ui.stage.StageScreen

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun PlanetPulseAcademy(
	viewModel: PlanetPulseAcademyViewModel
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	val modalBottomSheetState = rememberModalBottomSheetState(
		initialValue = ModalBottomSheetValue.Hidden,
		skipHalfExpanded = true
	)

	val bottomSheetNavigator = remember {
		BottomSheetNavigator(modalBottomSheetState)
	}

	val navController = rememberNavController(bottomSheetNavigator)

	val currentBackStack by navController.currentBackStackEntryAsState()

	var showBottomNavigation by remember { mutableStateOf(false) }

	val navigateTo: (Destination, builder: (NavOptionsBuilder.() -> Unit)?) -> Unit = { dest, builder ->
		if (builder != null) navController.navigate(dest.route, builder)
		else navController.navigate(dest.route)
	}

	LaunchedEffect(currentBackStack?.destination?.route) {
		showBottomNavigation = currentBackStack?.destination?.route in Destinations.bottomNavigation.map { it.route }
	}

	BaseScreenWrapper(
		viewModel = viewModel,
		containerColor = PPATheme.colorScheme.background,
		contentWindowInsets = WindowInsets.Zero
	) { scaffoldPadding ->
		ModalBottomSheetLayout(
			bottomSheetNavigator = bottomSheetNavigator,
			sheetBackgroundColor = MaterialTheme.colorScheme.surface,
			sheetContentColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.surface),
			scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f),
			sheetShape = MaterialTheme.shapes.large
		) {
			if (state.userCredential != null && state.userPreference != null) {
				Box(
					modifier = Modifier
						.fillMaxSize()
				) {
					CompositionLocalProvider(
						LocalBottomBarPadding provides if (showBottomNavigation) 80.dp else 0.dp
					) {
						NavHost(
							navController = navController,
							startDestination = when {
								state.userPreference!!.isFirstInstall -> Destinations.Onboarding.route.route
								!state.userCredential!!.isLoggedIn -> Destinations.Auth.route.route
								else -> Destinations.Main.route.route
							},
							modifier = Modifier
								.padding(scaffoldPadding)
						) {
							navigation(
								startDestination = Destinations.Onboarding.onboarding.route,
								route = Destinations.Onboarding.route.route
							) {
								composable(Destinations.Onboarding.onboarding.route) { backEntry ->
									OnboardingScreen(
										viewModel = hiltViewModel(backEntry),
										navigateTo = navigateTo
									)
								}
							}

							navigation(
								startDestination = Destinations.Auth.login.route,
								route = Destinations.Auth.route.route
							) {
								composable(Destinations.Auth.register.route) { backEntry ->
									RegisterScreen(
										viewModel = hiltViewModel(backEntry),
										onNavigateUp = navController::popBackStack
									)
								}

								composable(Destinations.Auth.login.route) { backEntry ->
									LoginScreen(
										viewModel = hiltViewModel(backEntry),
										navigateTo = navigateTo
									)
								}

								composable(Destinations.Auth.forgotPassword.route) { backEntry ->
									ForgotPasswordScreen(
										viewModel = hiltViewModel(backEntry),
										onNavigateUp = navController::popBackStack
									)
								}
							}

							navigation(
								startDestination = Destinations.Main.home.route,
								route = Destinations.Main.route.route
							) {
								composable(Destinations.Main.home.route) { backEntry ->
									HomeScreen(
										viewModel = hiltViewModel(backEntry),
										navigateTo = navigateTo
									)
								}

								composable(Destinations.Main.stage.route) { backEntry ->
									StageScreen(
										viewModel = hiltViewModel(backEntry),
										navigateTo = navigateTo
									)
								}

								composable(Destinations.Main.academy.route) { backEntry ->
									AcademyScreen(
										viewModel = hiltViewModel(backEntry),
										onNavigateUp = navController::popBackStack
									)
								}

								composable(Destinations.Main.profile.route) { backEntry ->
									ProfileScreen(
										viewModel = hiltViewModel(backEntry),
										onNavigateUp = navController::popBackStack
									)
								}

								composable(Destinations.Main.lesson.route) { backEntry ->
									LessonScreen(
										viewModel = hiltViewModel(backEntry),
										onNavigateUp = navController::popBackStack,
										navigateTo = navigateTo
									)
								}

								composable(Destinations.Main.quiz.route) { backEntry ->
									QuizScreen(
										viewModel = hiltViewModel(backEntry),
										onNavigateUp = navController::popBackStack,
									)
								}
							}
						}
					}

					BottomBar(
						show = showBottomNavigation,
						currentRoute = currentBackStack?.destination?.route,
						navigateTo = navigateTo,
						modifier = Modifier
							.align(Alignment.BottomCenter)
					)
				}
			}
		}
	}
}

@Composable
private fun BottomBar(
	show: Boolean,
	currentRoute: String?,
	modifier: Modifier = Modifier,
	navigateTo: (Destination, builder: (NavOptionsBuilder.() -> Unit)?) -> Unit
) {
	AnimatedVisibility(
		visible = show,
		enter = slideInVertically { it },
		exit = slideOutVertically { it },
		modifier = modifier
	) {
		NavigationBar {
			for (destination in Destinations.bottomNavigation) {
				NavigationBarItem(
					selected = currentRoute == destination.route,
					alwaysShowLabel = false,
					onClick = {
						navigateTo(destination) {
							popUpTo(Destinations.Main.home.route) {
								saveState = true
							}

							launchSingleTop = true
							restoreState = true
						}
					},
					label = {
						Text(stringResource(id = destination.title!!))
					},
					icon = {
						Icon(
							painter = painterResource(id = destination.icon!!),
							contentDescription = null
						)
					}
				)
			}
		}
	}
}

private fun navigateTo(navController: NavController, dest: Destination, inclusive: Boolean = false) {
	navController.navigate(dest.route)
}
