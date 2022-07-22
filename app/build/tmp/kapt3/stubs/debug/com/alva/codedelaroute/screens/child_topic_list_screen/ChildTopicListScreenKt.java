package com.alva.codedelaroute.screens.child_topic_list_screen;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 2, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\u001aZ\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00072\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000eH\u0007\u001a`\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0013\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00072\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000eH\u0007\u001a,\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00112\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0007\u001a\u0010\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\nH\u0007\u001a\u0018\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001dH\u0007\u00a8\u0006\u001e"}, d2 = {"ChildTopicList", "", "modifier", "Landroidx/compose/ui/Modifier;", "navController", "Landroidx/navigation/NavController;", "subTopics", "", "Lcom/alva/codedelaroute/models/Topic;", "subTopicProgressList", "Lcom/alva/codedelaroute/models/TopicProgress;", "topicViewModel", "Lcom/alva/codedelaroute/view_models/TopicViewModel;", "openDialog", "Landroidx/compose/runtime/MutableState;", "", "subTopicIdCallback", "", "ChildTopicListPanel", "parentTopic", "mainTopicProgress", "ChildTopicListScreen", "parentId", "questionViewModel", "Lcom/alva/codedelaroute/view_models/QuestionViewModel;", "ProgressCheckTable", "ProgressRow", "title", "count", "", "app_debug"})
public final class ChildTopicListScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void ChildTopicListScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    java.lang.String parentId, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.view_models.TopicViewModel topicViewModel, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.view_models.QuestionViewModel questionViewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ChildTopicList(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.Topic> subTopics, @org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.TopicProgress> subTopicProgressList, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.view_models.TopicViewModel topicViewModel, @org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<java.lang.Boolean> openDialog, @org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<java.lang.String> subTopicIdCallback) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ChildTopicListPanel(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.Topic> subTopics, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.Topic parentTopic, @org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.TopicProgress> subTopicProgressList, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.TopicProgress mainTopicProgress, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.view_models.TopicViewModel topicViewModel, @org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<java.lang.Boolean> openDialog, @org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<java.lang.String> subTopicIdCallback) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProgressCheckTable(@org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.TopicProgress mainTopicProgress) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProgressRow(@org.jetbrains.annotations.NotNull()
    java.lang.String title, int count) {
    }
}