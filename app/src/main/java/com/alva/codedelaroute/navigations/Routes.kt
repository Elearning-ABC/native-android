package com.alva.codedelaroute.navigations

enum class Routes {
    HomeScreen,
    ChildTopicListScreen,
    QuestionScreen,
    ReviewQuestionScreen,
    FinishTopicScreen,
    ReviewListScreen;

    companion object {
        fun fromRoute(route: String?): Routes = when (route?.substringBefore(delimiter = "/")) {
            HomeScreen.name -> HomeScreen
            ChildTopicListScreen.name -> ChildTopicListScreen
            QuestionScreen.name -> QuestionScreen
            ReviewQuestionScreen.name -> ReviewQuestionScreen
            FinishTopicScreen.name -> FinishTopicScreen
            ReviewListScreen.name -> ReviewListScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}