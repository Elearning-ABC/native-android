package com.alva.codedelaroute.repositories;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\u0011J\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\n0\u0011J\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\u0011J\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u00112\u0006\u0010\u0018\u001a\u00020\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2 = {"Lcom/alva/codedelaroute/repositories/QuestionProgressRepo;", "", "realm", "Lio/realm/kotlin/Realm;", "(Lio/realm/kotlin/Realm;)V", "getRealm", "()Lio/realm/kotlin/Realm;", "addOrUpdateQuestionProgressToRepo", "", "questionProgress", "Lcom/alva/codedelaroute/models/QuestionProgress;", "(Lcom/alva/codedelaroute/models/QuestionProgress;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearQuestionProgressData", "subTopicId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllAnsweredQuestions", "", "Lcom/alva/codedelaroute/models/Question;", "questionProgressList", "getAllQuestionProgress", "getAnsweredQuestionsByQuestionProgressList", "getProgressByQuestionId", "", "questionId", "app_debug"})
public final class QuestionProgressRepo {
    @org.jetbrains.annotations.NotNull()
    private final io.realm.kotlin.Realm realm = null;
    
    public QuestionProgressRepo(@org.jetbrains.annotations.NotNull()
    io.realm.kotlin.Realm realm) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.realm.kotlin.Realm getRealm() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getProgressByQuestionId(long questionId) {
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
}