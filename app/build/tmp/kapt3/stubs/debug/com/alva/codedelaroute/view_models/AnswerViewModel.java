package com.alva.codedelaroute.view_models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0085\u0001\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u000e2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u001a\u001a\u00020\u0011\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001b"}, d2 = {"Lcom/alva/codedelaroute/view_models/AnswerViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "onAnswerClickHandler", "", "answer", "Lcom/alva/codedelaroute/models/Answer;", "question", "Lcom/alva/codedelaroute/models/Question;", "questionProgress", "Lcom/alva/codedelaroute/models/QuestionProgress;", "questionViewModel", "Lcom/alva/codedelaroute/view_models/QuestionViewModel;", "panelColorState", "Landroidx/compose/runtime/MutableState;", "Landroidx/compose/ui/graphics/Color;", "enabled", "", "coroutine", "Lkotlinx/coroutines/CoroutineScope;", "answerStatus", "Lcom/alva/codedelaroute/utils/AnswerStatus;", "checkFinishedQuestion", "subTopicProgress", "Lcom/alva/codedelaroute/models/TopicProgress;", "mainTopicProgress", "isReviewScreen", "app_debug"})
public final class AnswerViewModel extends androidx.lifecycle.ViewModel {
    
    public AnswerViewModel() {
        super();
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.DelicateCoroutinesApi.class})
    public final void onAnswerClickHandler(@org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.Answer answer, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.Question question, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.QuestionProgress questionProgress, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.view_models.QuestionViewModel questionViewModel, @org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<androidx.compose.ui.graphics.Color> panelColorState, @org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<java.lang.Boolean> enabled, @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope coroutine, @org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<com.alva.codedelaroute.utils.AnswerStatus> answerStatus, @org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<java.lang.Boolean> checkFinishedQuestion, @org.jetbrains.annotations.Nullable()
    com.alva.codedelaroute.models.TopicProgress subTopicProgress, @org.jetbrains.annotations.Nullable()
    com.alva.codedelaroute.models.TopicProgress mainTopicProgress, boolean isReviewScreen) {
    }
}