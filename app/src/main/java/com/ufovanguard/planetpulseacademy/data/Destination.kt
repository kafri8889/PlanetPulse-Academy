package com.ufovanguard.planetpulseacademy.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

/**
 * @author kafri8889
 */

object DestinationRoute {
	const val REGISTER = "register"
	const val LOGIN = "login"
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

	val register = Destination(
		route = DestinationRoute.REGISTER
	)

	val login = Destination(
		route = DestinationRoute.LOGIN
	)

	val home = Destination(
		route = DestinationRoute.HOME
	)

}
