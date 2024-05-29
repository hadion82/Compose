package com.example.graph

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface NavComposableGraph {
    fun build(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    )
}

interface ComposableGraph {
    fun build(
        navGraphBuilder: NavGraphBuilder
    )
}

interface HomeGraph : NavComposableGraph {
    companion object {
        const val HOME_ROUTE = "HomeRoute"
    }
}

interface BookmarkGraph : NavComposableGraph {
    companion object {
        const val BOOK_MARK_ROUTE = "BookmarkRoute"
    }
}

interface ProfileGraph : ComposableGraph {
    interface Route {
        companion object {
            private const val PROFILE_BASE = "ProfileRoute"
            const val PROFILE = "$PROFILE_BASE?${Argument.PROFILE_ID}={${Argument.PROFILE_ID}}"
            fun makeRoute(arg: Int) =
                Uri.parse(PROFILE).buildUpon()
                    .appendQueryParameter(Argument.PROFILE_ID, arg.toString())
                    .build().toString()
        }
    }
    interface Argument {
        companion object {
            const val PROFILE_ID = "ProfileId"
        }
    }
}