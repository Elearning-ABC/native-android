package com.alva.codedelaroute.models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\'\u0018\u0000 I2\u00020\u0001:\u0001IB\u0005\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R \u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\r\"\u0004\b\u0018\u0010\u000fR\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0006\"\u0004\b!\u0010\bR\u001a\u0010\"\u001a\u00020#X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010\'R\u001a\u0010(\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0006\"\u0004\b*\u0010\bR\u001a\u0010+\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0006\"\u0004\b-\u0010\bR\u001a\u0010.\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0006\"\u0004\b0\u0010\bR \u00101\u001a\b\u0012\u0004\u0012\u00020\u00040\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\r\"\u0004\b3\u0010\u000fR\u001a\u00104\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\u001c\"\u0004\b6\u0010\u001eR\u001a\u00107\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u0010\u0013\"\u0004\b9\u0010\u0015R\u001a\u0010:\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u0010\u0006\"\u0004\b<\u0010\bR\u001a\u0010=\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010\u0006\"\u0004\b?\u0010\bR\u001a\u0010@\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bA\u0010\u0013\"\u0004\bB\u0010\u0015R\u001a\u0010C\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bD\u0010\u0006\"\u0004\bE\u0010\bR\u001a\u0010F\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bG\u0010\u0006\"\u0004\bH\u0010\b\u00a8\u0006J"}, d2 = {"Lcom/alva/codedelaroute/models/Question;", "Lio/realm/kotlin/types/RealmObject;", "()V", "audio", "", "getAudio", "()Ljava/lang/String;", "setAudio", "(Ljava/lang/String;)V", "choices", "Lio/realm/kotlin/types/RealmList;", "Lcom/alva/codedelaroute/models/Answer;", "getChoices", "()Lio/realm/kotlin/types/RealmList;", "setChoices", "(Lio/realm/kotlin/types/RealmList;)V", "correctAnswerNumber", "", "getCorrectAnswerNumber", "()I", "setCorrectAnswerNumber", "(I)V", "correctAnswers", "getCorrectAnswers", "setCorrectAnswers", "createDate", "", "getCreateDate", "()D", "setCreateDate", "(D)V", "explanation", "getExplanation", "setExplanation", "hasChild", "", "getHasChild", "()Z", "setHasChild", "(Z)V", "hint", "getHint", "setHint", "id", "getId", "setId", "image", "getImage", "setImage", "inCorrectAnswers", "getInCorrectAnswers", "setInCorrectAnswers", "lastUpdate", "getLastUpdate", "setLastUpdate", "level", "getLevel", "setLevel", "paragraphId", "getParagraphId", "setParagraphId", "parentId", "getParentId", "setParentId", "status", "getStatus", "setStatus", "text", "getText", "setText", "video", "getVideo", "setVideo", "Companion", "app_debug"})
public final class Question implements io.realm.kotlin.types.RealmObject {
    @org.jetbrains.annotations.NotNull()
    public static final com.alva.codedelaroute.models.Question.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    @io.realm.kotlin.types.annotations.PrimaryKey()
    private java.lang.String id = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String parentId = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String paragraphId = "";
    private int status = 0;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String text = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String video = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String audio = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String image = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String hint = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String explanation = "";
    private boolean hasChild = false;
    private int level = 0;
    private double createDate = 0.0;
    private double lastUpdate = 0.0;
    @org.jetbrains.annotations.NotNull()
    private io.realm.kotlin.types.RealmList<com.alva.codedelaroute.models.Answer> choices;
    @org.jetbrains.annotations.NotNull()
    private io.realm.kotlin.types.RealmList<java.lang.String> correctAnswers;
    @org.jetbrains.annotations.NotNull()
    private io.realm.kotlin.types.RealmList<java.lang.String> inCorrectAnswers;
    private int correctAnswerNumber = 0;
    
    public Question() {
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
    public final java.lang.String getParentId() {
        return null;
    }
    
    public final void setParentId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getParagraphId() {
        return null;
    }
    
    public final void setParagraphId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final int getStatus() {
        return 0;
    }
    
    public final void setStatus(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getText() {
        return null;
    }
    
    public final void setText(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getVideo() {
        return null;
    }
    
    public final void setVideo(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAudio() {
        return null;
    }
    
    public final void setAudio(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getImage() {
        return null;
    }
    
    public final void setImage(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHint() {
        return null;
    }
    
    public final void setHint(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getExplanation() {
        return null;
    }
    
    public final void setExplanation(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final boolean getHasChild() {
        return false;
    }
    
    public final void setHasChild(boolean p0) {
    }
    
    public final int getLevel() {
        return 0;
    }
    
    public final void setLevel(int p0) {
    }
    
    public final double getCreateDate() {
        return 0.0;
    }
    
    public final void setCreateDate(double p0) {
    }
    
    public final double getLastUpdate() {
        return 0.0;
    }
    
    public final void setLastUpdate(double p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.realm.kotlin.types.RealmList<com.alva.codedelaroute.models.Answer> getChoices() {
        return null;
    }
    
    public final void setChoices(@org.jetbrains.annotations.NotNull()
    io.realm.kotlin.types.RealmList<com.alva.codedelaroute.models.Answer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.realm.kotlin.types.RealmList<java.lang.String> getCorrectAnswers() {
        return null;
    }
    
    public final void setCorrectAnswers(@org.jetbrains.annotations.NotNull()
    io.realm.kotlin.types.RealmList<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.realm.kotlin.types.RealmList<java.lang.String> getInCorrectAnswers() {
        return null;
    }
    
    public final void setInCorrectAnswers(@org.jetbrains.annotations.NotNull()
    io.realm.kotlin.types.RealmList<java.lang.String> p0) {
    }
    
    public final int getCorrectAnswerNumber() {
        return 0;
    }
    
    public final void setCorrectAnswerNumber(int p0) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\t\u0010\u0003\u001a\u00020\u0001H\u00d6\u0001J\t\u0010\u0004\u001a\u00020\u0001H\u00d6\u0001\u00a8\u0006\u0005"}, d2 = {"Lcom/alva/codedelaroute/models/Question$Companion;", "", "()V", "io_realm_kotlin_newInstance", "io_realm_kotlin_schema", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}