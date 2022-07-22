package com.alva.codedelaroute.repositories;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u0019\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J\u0019\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u001aH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ!\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001aH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00120 J\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00120 J\f\u0010$\u001a\b\u0012\u0004\u0012\u00020%0 J\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00020!0 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00120 J\u0014\u0010\'\u001a\b\u0012\u0004\u0012\u00020(0 2\u0006\u0010)\u001a\u00020\u001aJ\u0016\u0010*\u001a\b\u0012\u0004\u0012\u00020+0 2\u0006\u0010,\u001a\u00020\u001aH\u0002J\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010,\u001a\u00020\u001aJ\u000e\u0010.\u001a\u00020%2\u0006\u0010/\u001a\u00020\u001aJ\u000e\u00100\u001a\u00020\u00162\u0006\u00101\u001a\u00020\u001aR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u00062"}, d2 = {"Lcom/alva/codedelaroute/repositories/SqlRepo;", "", "()V", "questionProgressRepo", "Lcom/alva/codedelaroute/repositories/QuestionProgressRepo;", "questionRepo", "Lcom/alva/codedelaroute/repositories/QuestionRepo;", "realm", "Lio/realm/kotlin/Realm;", "topicProgressRepo", "Lcom/alva/codedelaroute/repositories/TopicProgressRepo;", "topicQuestionRepo", "Lcom/alva/codedelaroute/repositories/TopicQuestionRepo;", "topicRepo", "Lcom/alva/codedelaroute/repositories/TopicRepo;", "addOrUpdateQuestionProgressToRepo", "", "questionProgress", "Lcom/alva/codedelaroute/models/QuestionProgress;", "(Lcom/alva/codedelaroute/models/QuestionProgress;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addOrUpdateTopicProgressToRepo", "topicProgress", "Lcom/alva/codedelaroute/models/TopicProgress;", "(Lcom/alva/codedelaroute/models/TopicProgress;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearQuestionProgressData", "subTopicId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearSubTopicProgressData", "parentTopicId", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllAnsweredQuestions", "", "Lcom/alva/codedelaroute/models/Question;", "questionProgressList", "getAllQuestionProgress", "getAllTopic", "Lcom/alva/codedelaroute/models/Topic;", "getAnsweredQuestionsByQuestionProgressList", "getProgressByQuestionId", "", "questionId", "getQuestionIdListByParentId", "", "parentId", "getQuestionsByParentId", "getTopicById", "id", "getTopicProgressByTopicId", "topicId", "app_debug"})
public final class SqlRepo {
    @org.jetbrains.annotations.NotNull()
    public static final com.alva.codedelaroute.repositories.SqlRepo INSTANCE = null;
    private static final io.realm.kotlin.Realm realm = null;
    private static final com.alva.codedelaroute.repositories.TopicRepo topicRepo = null;
    private static final com.alva.codedelaroute.repositories.QuestionRepo questionRepo = null;
    private static final com.alva.codedelaroute.repositories.TopicQuestionRepo topicQuestionRepo = null;
    private static final com.alva.codedelaroute.repositories.QuestionProgressRepo questionProgressRepo = null;
    private static final com.alva.codedelaroute.repositories.TopicProgressRepo topicProgressRepo = null;
    
    private SqlRepo() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.alva.codedelaroute.models.Topic> getAllTopic() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.alva.codedelaroute.models.Topic getTopicById(long id) {
        return null;
    }
    
    private final java.util.List<java.lang.String> getQuestionIdListByParentId(long parentId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.alva.codedelaroute.models.Question> getQuestionsByParentId(long parentId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addOrUpdateQuestionProgressToRepo(@org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.QuestionProgress questionProgress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearQuestionProgressData(long subTopicId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.alva.codedelaroute.models.Question> getAllAnsweredQuestions(@org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.QuestionProgress> questionProgressList) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.alva.codedelaroute.models.QuestionProgress> getAllQuestionProgress() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.alva.codedelaroute.models.Question> getAnsweredQuestionsByQuestionProgressList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.QuestionProgress> questionProgressList) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getProgressByQuestionId(long questionId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.alva.codedelaroute.models.TopicProgress getTopicProgressByTopicId(long topicId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addOrUpdateTopicProgressToRepo(@org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.TopicProgress topicProgress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearSubTopicProgressData(long subTopicId, long parentTopicId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
}