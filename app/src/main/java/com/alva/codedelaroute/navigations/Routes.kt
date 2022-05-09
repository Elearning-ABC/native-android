package com.alva.codedelaroute.navigations

import com.alva.codedelaroute.screens.HomeScreen

enum class Routes {
    HomeScreen,
    ChildTopicListScreen,
    QuestionScreen;

    companion object {
        fun fromRoute(route: String?): Routes = when (route?.substringBefore(delimiter = "/")) {
            HomeScreen.name -> HomeScreen
            ChildTopicListScreen.name -> ChildTopicListScreen
            QuestionScreen.name -> QuestionScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}