package com.alva.codedelaroute.models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0015\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001a\u0010\u0012\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001a\u0010\u0015\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\b\u00a8\u0006\u0019"}, d2 = {"Lcom/alva/codedelaroute/models/StateInfo;", "Lio/realm/kotlin/types/RealmObject;", "()V", "flag", "", "getFlag", "()Ljava/lang/String;", "setFlag", "(Ljava/lang/String;)V", "governmentAgency", "getGovernmentAgency", "setGovernmentAgency", "id", "getId", "setId", "name", "getName", "setName", "parentId", "getParentId", "setParentId", "shortName", "getShortName", "setShortName", "Companion", "app_debug"})
public final class StateInfo implements io.realm.kotlin.types.RealmObject {
    @org.jetbrains.annotations.NotNull()
    public static final com.alva.codedelaroute.models.StateInfo.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    @io.realm.kotlin.types.annotations.PrimaryKey()
    private java.lang.String id = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String parentId = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String name = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String shortName = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String governmentAgency = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String flag = "";
    
    public StateInfo() {
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
    public final java.lang.String getName() {
        return null;
    }
    
    public final void setName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getShortName() {
        return null;
    }
    
    public final void setShortName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGovernmentAgency() {
        return null;
    }
    
    public final void setGovernmentAgency(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFlag() {
        return null;
    }
    
    public final void setFlag(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\t\u0010\u0003\u001a\u00020\u0001H\u00d6\u0001J\t\u0010\u0004\u001a\u00020\u0001H\u00d6\u0001\u00a8\u0006\u0005"}, d2 = {"Lcom/alva/codedelaroute/models/StateInfo$Companion;", "", "()V", "io_realm_kotlin_newInstance", "io_realm_kotlin_schema", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}