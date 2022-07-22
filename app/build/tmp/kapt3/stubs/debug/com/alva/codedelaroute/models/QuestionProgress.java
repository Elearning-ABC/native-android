package com.alva.codedelaroute.models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u0006\n\u0002\b\u0018\u0018\u0000 32\u00020\u0001:\u00013B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR \u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R \u0010!\u001a\b\u0012\u0004\u0012\u00020\n0\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0013\"\u0004\b#\u0010\u0015R\u001a\u0010$\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0018\"\u0004\b&\u0010\u001aR\u001a\u0010\'\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\f\"\u0004\b)\u0010\u000eR\u001a\u0010*\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0018\"\u0004\b,\u0010\u001aR \u0010-\u001a\b\u0012\u0004\u0012\u00020\n0\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0013\"\u0004\b/\u0010\u0015R\u001a\u00100\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u0018\"\u0004\b2\u0010\u001a\u00a8\u00064"}, d2 = {"Lcom/alva/codedelaroute/models/QuestionProgress;", "Lio/realm/kotlin/types/RealmObject;", "()V", "bookmark", "", "getBookmark", "()Z", "setBookmark", "(Z)V", "boxNum", "", "getBoxNum", "()I", "setBoxNum", "(I)V", "choiceSelectedIds", "Lio/realm/kotlin/types/RealmList;", "", "getChoiceSelectedIds", "()Lio/realm/kotlin/types/RealmList;", "setChoiceSelectedIds", "(Lio/realm/kotlin/types/RealmList;)V", "id", "getId", "()Ljava/lang/String;", "setId", "(Ljava/lang/String;)V", "lastUpdate", "", "getLastUpdate", "()D", "setLastUpdate", "(D)V", "progress", "getProgress", "setProgress", "questionId", "getQuestionId", "setQuestionId", "round", "getRound", "setRound", "stateId", "getStateId", "setStateId", "times", "getTimes", "setTimes", "topicId", "getTopicId", "setTopicId", "Companion", "app_debug"})
public final class QuestionProgress implements io.realm.kotlin.types.RealmObject {
    @org.jetbrains.annotations.NotNull()
    public static final com.alva.codedelaroute.models.QuestionProgress.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    @io.realm.kotlin.types.annotations.PrimaryKey()
    private java.lang.String id = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String questionId = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String stateId = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String topicId = "";
    @org.jetbrains.annotations.NotNull()
    private io.realm.kotlin.types.RealmList<java.lang.Integer> progress;
    @org.jetbrains.annotations.NotNull()
    private io.realm.kotlin.types.RealmList<java.lang.String> choiceSelectedIds;
    private int boxNum = 0;
    @org.jetbrains.annotations.NotNull()
    private io.realm.kotlin.types.RealmList<java.lang.Integer> times;
    private int round = 0;
    private double lastUpdate = 0.0;
    private boolean bookmark = false;
    
    public QuestionProgress() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    public final void setId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getQuestionId() {
        return null;
    }
    
    public final void setQuestionId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStateId() {
        return null;
    }
    
    public final void setStateId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTopicId() {
        return null;
    }
    
    public final void setTopicId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.realm.kotlin.types.RealmList<java.lang.Integer> getProgress() {
        return null;
    }
    
    public final void setProgress(@org.jetbrains.annotations.NotNull()
    io.realm.kotlin.types.RealmList<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.realm.kotlin.types.RealmList<java.lang.String> getChoiceSelectedIds() {
        return null;
    }
    
    public final void setChoiceSelectedIds(@org.jetbrains.annotations.NotNull()
    io.realm.kotlin.types.RealmList<java.lang.String> p0) {
    }
    
    public final int getBoxNum() {
        return 0;
    }
    
    public final void setBoxNum(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.realm.kotlin.types.RealmList<java.lang.Integer> getTimes() {
        return null;
    }
    
    public final void setTimes(@org.jetbrains.annotations.NotNull()
    io.realm.kotlin.types.RealmList<java.lang.Integer> p0) {
    }
    
    public final int getRound() {
        return 0;
    }
    
    public final void setRound(int p0) {
    }
    
    public final double getLastUpdate() {
        return 0.0;
    }
    
    public final void setLastUpdate(double p0) {
    }
    
    public final boolean getBookmark() {
        return false;
    }
    
    public final void setBookmark(boolean p0) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\t\u0010\u0003\u001a\u00020\u0001H\u00d6\u0001J\t\u0010\u0004\u001a\u00020\u0001H\u00d6\u0001\u00a8\u0006\u0005"}, d2 = {"Lcom/alva/codedelaroute/models/QuestionProgress$Companion;", "", "()V", "io_realm_kotlin_newInstance", "io_realm_kotlin_schema", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}