package com.alva.codedelaroute.view_models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0019\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0019\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0019H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\tJ\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001c0\tJ\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\u0016\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\nJ\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010%\u001a\u00020\u0019H\u0002J\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u001c0\tJ\u000e\u0010(\u001a\u00020)2\u0006\u0010%\u001a\u00020\u0019J\u0014\u0010*\u001a\u00020)2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010-\u001a\u00020\u0019J\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001c0\t2\u0006\u0010/\u001a\u000200J\"\u00101\u001a\u00020\n2\u0006\u0010-\u001a\u00020\u00192\b\b\u0002\u0010%\u001a\u00020\u00192\b\b\u0002\u00102\u001a\u000203J\u0016\u00104\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010/\u001a\u000200H\u0002J\u0014\u00105\u001a\b\u0012\u0004\u0012\u00020\u001c0\t2\u0006\u00106\u001a\u00020\u0019J\u0010\u00107\u001a\u0002002\u0006\u0010\u0015\u001a\u00020\nH\u0002J\u000e\u00108\u001a\u00020)2\u0006\u0010%\u001a\u00020\u0019J\u0014\u00109\u001a\u00020)2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u001c\u0010:\u001a\u00020\u001c2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u001c0\t2\u0006\u00106\u001a\u00020\u0019J\"\u0010<\u001a\u00020\u001c2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u001c0\t2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u0016\u0010>\u001a\u0002032\u0006\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\nJ\u0018\u0010?\u001a\u0002032\u0006\u0010-\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\u0019H\u0002J=\u0010@\u001a\u00020\u00142\u0006\u0010A\u001a\u00020B2\u0006\u0010#\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\u001c2\b\u0010C\u001a\u0004\u0018\u00010D2\b\u0010E\u001a\u0004\u0018\u00010DH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010FJ\u0006\u0010G\u001a\u00020\u0014J\u0006\u0010H\u001a\u00020\u0014J\u000e\u0010I\u001a\u00020\u00142\u0006\u0010/\u001a\u000200R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0007R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0007R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0007R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0007\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006J"}, d2 = {"Lcom/alva/codedelaroute/view_models/QuestionViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "allAnsweredQuestionsCount", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getAllAnsweredQuestionsCount", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "allQuestionProgressList", "", "Lcom/alva/codedelaroute/models/QuestionProgress;", "favoriteQuestionsCount", "getFavoriteQuestionsCount", "mediumQuestionsCount", "getMediumQuestionsCount", "strongQuestionsCount", "getStrongQuestionsCount", "weakQuestionsCount", "getWeakQuestionsCount", "addOrUpdateQuestionProgressToRepo", "", "questionProgress", "(Lcom/alva/codedelaroute/models/QuestionProgress;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearQuestionProgressData", "subTopicId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllAnsweredQuestion", "Lcom/alva/codedelaroute/models/Question;", "getAllFavoriteQuestionProgress", "getAllFavoriteQuestions", "getAllQuestionProgress", "getAnswerStatus", "Lcom/alva/codedelaroute/utils/AnswerStatus;", "question", "currentQuestionProgress", "getAnsweredQuestionsByTopicId", "topicId", "getEmptyQuestionProgressListProgress", "questions", "getFalseQuestionsPercent", "", "getFalseQuestionsPercentByQuestionProgressList", "questionProgressList", "getProgressByQuestionId", "questionId", "getQuestionListByReviewProperty", "reviewQuestionProperty", "Lcom/alva/codedelaroute/utils/ReviewQuestionProperty;", "getQuestionProgressByQuestionId", "isInReviewScreen", "", "getQuestionProgressListByReviewProperty", "getQuestionsByParentId", "parentId", "getReviewQuestionProperty", "getTrueQuestionsPercent", "getTrueQuestionsPercentByQuestionProgressList", "goToNextQuestion", "questionList", "goToNextReviewQuestion", "tempQuestionProgressList", "isFinishQuestion", "isQuestionAnsweredTrue", "onQuestionAnswerClick", "answerId", "", "subTopicProgress", "Lcom/alva/codedelaroute/models/TopicProgress;", "mainTopicProgress", "(Ljava/lang/String;Lcom/alva/codedelaroute/models/QuestionProgress;Lcom/alva/codedelaroute/models/Question;Lcom/alva/codedelaroute/models/TopicProgress;Lcom/alva/codedelaroute/models/TopicProgress;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateAllAnswerQuestionsCount", "updateFavoriteQuestionsCount", "updateQuestionListCountByReviewProperty", "app_debug"})
public final class QuestionViewModel extends androidx.lifecycle.ViewModel {
    private java.util.List<com.alva.codedelaroute.models.QuestionProgress> allQuestionProgressList;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> weakQuestionsCount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> mediumQuestionsCount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> strongQuestionsCount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> allAnsweredQuestionsCount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> favoriteQuestionsCount = null;
    
    public QuestionViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> getWeakQuestionsCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> getMediumQuestionsCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> getStrongQuestionsCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> getAllAnsweredQuestionsCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> getFavoriteQuestionsCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.alva.codedelaroute.models.QuestionProgress> getEmptyQuestionProgressListProgress(@org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.Question> questions) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.alva.codedelaroute.models.Question> getQuestionsByParentId(long parentId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.alva.codedelaroute.models.QuestionProgress getQuestionProgressByQuestionId(long questionId, long topicId, boolean isInReviewScreen) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addOrUpdateQuestionProgressToRepo(@org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.QuestionProgress questionProgress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    private final java.util.List<com.alva.codedelaroute.models.QuestionProgress> getAnsweredQuestionsByTopicId(long topicId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.alva.codedelaroute.models.Question goToNextQuestion(@org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.Question> questionList, long parentId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.alva.codedelaroute.models.Question goToNextReviewQuestion(@org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.Question> questionList, @org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.QuestionProgress> tempQuestionProgressList) {
        return null;
    }
    
    private final boolean isQuestionAnsweredTrue(long questionId, long topicId) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object onQuestionAnswerClick(@org.jetbrains.annotations.NotNull()
    java.lang.String answerId, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.QuestionProgress currentQuestionProgress, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.Question question, @org.jetbrains.annotations.Nullable()
    com.alva.codedelaroute.models.TopicProgress subTopicProgress, @org.jetbrains.annotations.Nullable()
    com.alva.codedelaroute.models.TopicProgress mainTopicProgress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    public final boolean isFinishQuestion(@org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.Question question, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.QuestionProgress currentQuestionProgress) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.alva.codedelaroute.utils.AnswerStatus getAnswerStatus(@org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.Question question, @org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.models.QuestionProgress currentQuestionProgress) {
        return null;
    }
    
    public final float getTrueQuestionsPercent(long topicId) {
        return 0.0F;
    }
    
    public final float getFalseQuestionsPercent(long topicId) {
        return 0.0F;
    }
    
    public final float getTrueQuestionsPercentByQuestionProgressList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.QuestionProgress> questionProgressList) {
        return 0.0F;
    }
    
    public final float getFalseQuestionsPercentByQuestionProgressList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.alva.codedelaroute.models.QuestionProgress> questionProgressList) {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearQuestionProgressData(long subTopicId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    private final com.alva.codedelaroute.utils.ReviewQuestionProperty getReviewQuestionProperty(com.alva.codedelaroute.models.QuestionProgress questionProgress) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.alva.codedelaroute.models.Question> getAllAnsweredQuestion() {
        return null;
    }
    
    public final void updateAllAnswerQuestionsCount() {
    }
    
    private final java.util.List<com.alva.codedelaroute.models.QuestionProgress> getAllQuestionProgress() {
        return null;
    }
    
    private final java.util.List<com.alva.codedelaroute.models.QuestionProgress> getQuestionProgressListByReviewProperty(com.alva.codedelaroute.utils.ReviewQuestionProperty reviewQuestionProperty) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.alva.codedelaroute.models.Question> getQuestionListByReviewProperty(@org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.utils.ReviewQuestionProperty reviewQuestionProperty) {
        return null;
    }
    
    public final void updateQuestionListCountByReviewProperty(@org.jetbrains.annotations.NotNull()
    com.alva.codedelaroute.utils.ReviewQuestionProperty reviewQuestionProperty) {
    }
    
    private final java.util.List<com.alva.codedelaroute.models.QuestionProgress> getAllFavoriteQuestionProgress() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.alva.codedelaroute.models.Question> getAllFavoriteQuestions() {
        return null;
    }
    
    public final void updateFavoriteQuestionsCount() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getProgressByQuestionId(long questionId) {
        return null;
    }
}