package com.alva.codedelaroute.view_models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ!\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u0004\u0018\u00010\u0005J\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0018\u001a\u00020\u0005J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u001a\u001a\u00020\u0012J\u000e\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u0012J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\r\u001a\u00020\u0012R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001f"}, d2 = {"Lcom/alva/codedelaroute/view_models/TopicViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "allTopic", "", "Lcom/alva/codedelaroute/models/Topic;", "mainTopics", "getMainTopics", "()Ljava/util/List;", "setMainTopics", "(Ljava/util/List;)V", "checkFinishedTopic", "", "topicId", "", "clearSubTopicProgressData", "", "subTopicId", "", "parentTopicId", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getContinueTopic", "getMainTopic", "getNextTopicId", "currentTopic", "getSubTopic", "parentId", "getTopicById", "id", "getTopicProgressByTopicId", "Lcom/alva/codedelaroute/models/TopicProgress;", "app_debug"})
public final class TopicViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.alva.codedelaroute.models.Topic> mainTopics;
    private java.util.List<com.alva.codedelaroute.models.Topic> allTopic;
    
    public TopicViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.alva.codedelaroute.models.Topic> getMainTopics() {
        return null;
    }
    
    public final void setMainTopics(@org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.Topic> p0) {
    }
    
    private final java.util.List<com.alva.codedelaroute.models.Topic> getMainTopic() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.alva.codedelaroute.models.Topic> getSubTopic(long parentId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.alva.codedelaroute.models.Topic getTopicById(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.alva.codedelaroute.models.TopicProgress getTopicProgressByTopicId(long topicId) {
        return null;
    }
    
    public final boolean checkFinishedTopic(@org.jetbrains.annotations.NotNull()
    java.lang.String topicId) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearSubTopicProgressData(long subTopicId, long parentTopicId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNextTopicId(@org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.Topic currentTopic) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.alva.codedelaroute.models.Topic getContinueTopic() {
        return null;
    }
}