package com.alva.codedelaroute.utils

enum class AnswerStatus {
    None,
    True,
    False,
    TryAgainWithTrue,
    TryAgainWithFalse
}

enum class ReviewQuestionProperty {
    WeakQuestions,
    MediumQuestions,
    StrongQuestions,
    AllFamiliarQuestions,
    YourFavoriteQuestions,
    None
}

enum class GameType {
    Practice,
    Test,
    Review
}

enum class TestSetting {
    Easy,
    Medium,
    Hardest
}

enum class TestStatus {
    None,
    Playing,
    Done
}

enum class TestIconStatus {
    None,
    Passed,
    Failed
}