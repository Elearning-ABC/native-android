package com.alva.codedelaroute.navigations

enum class Routes {
    SplashScreen,
    OnBoardingScreen,
    HomeScreen,
    ChildTopicListScreen,
    QuestionScreen,
    ReviewQuestionScreen,
    FinishTopicScreen,
    ReviewListScreen;

    companion object {
        fun fromRoute(route: String?): Routes = when (route?.substringBefore(delimiter = "/")) {
            SplashScreen.name -> SplashScreen
            OnBoardingScreen.name -> OnBoardingScreen
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