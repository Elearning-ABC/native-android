package com.alva.codedelaroute.models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u0006\n\u0002\b!\u0018\u0000 92\u00020\u0001:\u00019B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\u001a\u0010\u0015\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000f\"\u0004\b\u0017\u0010\u0011R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u000f\"\u0004\b \u0010\u0011R\u001a\u0010!\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001a\u0010$\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\bR\u001a\u0010\'\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u000f\"\u0004\b)\u0010\u0011R\u001a\u0010*\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0006\"\u0004\b,\u0010\bR\u001a\u0010-\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u000f\"\u0004\b/\u0010\u0011R\u001a\u00100\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u000f\"\u0004\b2\u0010\u0011R\u001a\u00103\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\u0006\"\u0004\b5\u0010\bR\u001a\u00106\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b7\u0010\u0006\"\u0004\b8\u0010\b\u00a8\u0006:"}, d2 = {"Lcom/alva/codedelaroute/models/Topic;", "Lio/realm/kotlin/types/RealmObject;", "()V", "allowMistakes", "", "getAllowMistakes", "()I", "setAllowMistakes", "(I)V", "childrenType", "getChildrenType", "setChildrenType", "description", "", "getDescription", "()Ljava/lang/String;", "setDescription", "(Ljava/lang/String;)V", "icon", "getIcon", "setIcon", "id", "getId", "setId", "lastUpdate", "", "getLastUpdate", "()D", "setLastUpdate", "(D)V", "name", "getName", "setName", "numMaster", "getNumMaster", "setNumMaster", "orderIndex", "getOrderIndex", "setOrderIndex", "parentId", "getParentId", "setParentId", "status", "getStatus", "setStatus", "tag", "getTag", "setTag", "thumbnail", "getThumbnail", "setThumbnail", "totalQuestion", "getTotalQuestion", "setTotalQuestion", "type", "getType", "setType", "Companion", "app_debug"})
public final class Topic implements io.realm.kotlin.types.RealmObject {
    @org.jetbrains.annotations.NotNull()
    public static final com.alva.codedelaroute.models.Topic.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    @io.realm.kotlin.types.annotations.PrimaryKey()
    private java.lang.String id = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String parentId = "";
    private int status = 0;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String name = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String description = "";
    private int orderIndex = 0;
    private int totalQuestion = 0;
    private int allowMistakes = 0;
    private int numMaster = 0;
    private int childrenType = 0;
    private int type = 0;
    private double lastUpdate = 0.0;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String thumbnail = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String icon = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String tag = "";
    
    public Topic() {
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
    
    public final int getStatus() {
        return 0;
    }
    
    public final void setStatus(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    public final void setName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    public final void setDescription(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final int getOrderIndex() {
        return 0;
    }
    
    public final void setOrderIndex(int p0) {
    }
    
    public final int getTotalQuestion() {
        return 0;
    }
    
    public final void setTotalQuestion(int p0) {
    }
    
    public final int getAllowMistakes() {
        return 0;
    }
    
    public final void setAllowMistakes(int p0) {
    }
    
    public final int getNumMaster() {
        return 0;
    }
    
    public final void setNumMaster(int p0) {
    }
    
    public final int getChildrenType() {
        return 0;
    }
    
    public final void setChildrenType(int p0) {
    }
    
    public final int getType() {
        return 0;
    }
    
    public final void setType(int p0) {
    }
    
    public final double getLastUpdate() {
        return 0.0;
    }
    
    public final void setLastUpdate(double p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getThumbnail() {
        return null;
    }
    
    public final void setThumbnail(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIcon() {
        return null;
    }
    
    public final void setIcon(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTag() {
        return null;
    }
    
    public final void setTag(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\t\u0010\u0003\u001a\u00020\u0001H\u00d6\u0001J\t\u0010\u0004\u001a\u00020\u0001H\u00d6\u0001\u00a8\u0006\u0005"}, d2 = {"Lcom/alva/codedelaroute/models/Topic$Companion;", "", "()V", "io_realm_kotlin_newInstance", "io_realm_kotlin_schema", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}