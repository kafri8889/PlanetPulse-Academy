package com.ufovanguard.planetpulseacademy.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import com.ufovanguard.planetpulseacademy.R

/**
 * @author kafri8889
 */

object DestinationRoute {
	const val FORGOT_PASSWORD = "forgot_password"
	const val ONBOARDING = "onboarding"
	const val REGISTER = "register"
	const val ACADEMY = "academy"
	const val PROFILE = "profile"
	const val LESSON = "lesson"
	const val STAGE = "stage"
	const val LOGIN = "login"
	const val QUIZ = "quiz"
	const val HOME = "home"
}

/**
 * Key for argument
 */
object DestinationArgument {
}

data class Destination(
	val route: String,
	val arguments: List<NamedNavArgument> = emptyList(),
	val deepLinks: List<NavDeepLink> = emptyList(),
	@StringRes val title: Int? = null,
	@StringRes val subtitle: Int? = null,
	@DrawableRes val icon: Int? = null
) {
	/**
	 * if you want to navigate to another screen with arguments, use this
	 * @param value {key: value}
	 * @author kafri8889
	 */
	fun createRoute(vararg value: Pair<Any, Any?>): Destination {
		var mRoute = route

		value.forEach { (key, value) ->
			mRoute = mRoute.replace("{$key}", value.toString())
		}

		return Destination(mRoute, arguments)
	}

	companion object {
		/**
		 * if you want to create screen route with arguments, for example:
		 *```
		 * "$ROUTE?" +
		 * "$ARG_1={$ARG_1}&" +
		 * "$ARG_2={$ARG_2}"
		 * ```
		 *
		 * with [buildRoute]:
		 * ```
		 * Destination.buildRoute(
		 *     ROUTE,
		 *     ARG_1,
		 *     ARG_2
		 * )
		 * ```
		 *
		 * @author kafri8889
		 */
		fun buildRoute(
			route: String,
			vararg args: String
		): String {
			return StringBuilder().apply {
				append("$route${if (args.isNotEmpty()) "?" else ""}")
				for (i in args.indices) {
					append("${args[i]}={${args[i]}}")
					if (i != args.lastIndex) append("&")
				}
			}.toString()
		}
	}
}

object Destinations {

	/**
	 * Onboarding graph
	 */
	object Onboarding {
		val route = Destination(
			route = "root_onboarding"
		)

		val onboarding = Destination(
			route = DestinationRoute.ONBOARDING
		)
	}

	/**
	 * Auth graph
	 */
	object Auth {
		val route = Destination(
			route = "root_auth"
		)

		val register = Destination(
			route = DestinationRoute.REGISTER
		)

		val login = Destination(
			route = DestinationRoute.LOGIN
		)

		val forgotPassword = Destination(
			route = DestinationRoute.FORGOT_PASSWORD
		)
	}

	/**
	 * Main graph when user is logged in
	 */
	object Main {
		val route = Destination(
			route = "root_main"
		)

		val home = Destination(
			route = DestinationRoute.HOME,
			title = R.string.home,
			icon = R.drawable.ic_home_filled
		)

		val academy = Destination(
			route = DestinationRoute.ACADEMY,
			title = R.string.academy,
			icon = R.drawable.ic_book_filled
		)

		val profile = Destination(
			route = DestinationRoute.PROFILE,
			title = R.string.profile,
			icon = R.drawable.ic_user_filled
		)

		val quiz = Destination(
			route = DestinationRoute.QUIZ
		)

		val stage = Destination(
			route = DestinationRoute.STAGE
		)

		val lesson = Destination(
			route = DestinationRoute.LESSON,
			title = R.string.lesson,
			icon = R.drawable.ic_book_filled
		)
	}

	val bottomNavigation = listOf(
		Main.home,
		Main.academy,
		Main.profile
	)
}
