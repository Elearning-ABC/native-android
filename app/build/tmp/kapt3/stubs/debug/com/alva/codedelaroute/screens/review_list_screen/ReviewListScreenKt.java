package com.alva.codedelaroute.screens.review_list_screen;

import java.lang.System;

@kotlin.OptIn(markerClass = {kotlinx.coroutines.DelicateCoroutinesApi.class})
@kotlin.Metadata(mv = {1, 6, 0}, k = 2, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010!\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aZ\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u00032\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007\u001a\"\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007\u00a8\u0006\u0018"}, d2 = {"ReviewItemCard", "", "showAnswer", "Landroidx/compose/runtime/MutableState;", "", "progressList", "", "", "item", "Lcom/alva/codedelaroute/models/Question;", "imageBitmap", "Landroidx/compose/ui/graphics/ImageBitmap;", "choices", "", "Lcom/alva/codedelaroute/models/Answer;", "questionViewModel", "Lcom/alva/codedelaroute/view_models/QuestionViewModel;", "questionId", "", "ReviewListScreen", "navController", "Landroidx/navigation/NavController;", "reviewQuestionProperty", "Lcom/alva/codedelaroute/utils/ReviewQuestionProperty;", "app_debug"})
public final class ReviewListScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void ReviewListScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.utils.ReviewQuestionProperty reviewQuestionProperty, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.view_models.QuestionViewModel questionViewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ReviewItemCard(@org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<java.lang.Boolean> showAnswer, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> progressList, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.Question item, @org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<androidx.compose.ui.graphics.ImageBitmap> imageBitmap, @org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.Answer> choices, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.view_models.QuestionViewModel questionViewModel, @org.jetbrains.annotations.NotNull()
    java.lang.String questionId) {
    }
}