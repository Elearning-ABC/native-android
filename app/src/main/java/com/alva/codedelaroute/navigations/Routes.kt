package com.alva.codedelaroute.navigations

enum class Routes {
    SplashScreen,
    OnBoardingScreen,
    HomeScreen,
    TopicScreen,
    PracticeGameScreen,
    ReviewGameScreen,
    TestGameScreen,
    TopicPassedScreen,
    TestDoneScreen,
    ReviewListScreen,
    TestOptionScreen;

    companion object {
        fun fromRoute(route: String?): Routes = when (route?.substringBefore(delimiter = "/")) {
            SplashScreen.name -> SplashScreen
            OnBoardingScreen.name -> OnBoardingScreen
            HomeScreen.name -> HomeScreen
            TopicScreen.name -> TopicScreen
            PracticeGameScreen.name -> PracticeGameScreen
            ReviewGameScreen.name -> ReviewGameScreen
            TestGameScreen.name -> TestGameScreen
            TopicPassedScreen.name -> TopicPassedScreen
            TestDoneScreen.name -> TestDoneScreen
            ReviewListScreen.name -> ReviewListScreen
            TestOptionScreen.name -> TestOptionScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}