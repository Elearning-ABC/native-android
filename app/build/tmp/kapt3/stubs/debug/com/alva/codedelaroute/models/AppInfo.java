package com.alva.codedelaroute.models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\u001e\b\u0016\u0018\u0000 62\u00020\u0001:\u00016B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001a\u0010!\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001a\u0010$\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\bR\u001a\u0010\'\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0006\"\u0004\b)\u0010\bR\u001a\u0010*\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0006\"\u0004\b,\u0010\bR\u001a\u0010-\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0006\"\u0004\b/\u0010\bR\u001a\u00100\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u0006\"\u0004\b2\u0010\bR\u001a\u00103\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\u0006\"\u0004\b5\u0010\b\u00a8\u00067"}, d2 = {"Lcom/alva/codedelaroute/models/AppInfo;", "Lio/realm/kotlin/types/RealmObject;", "()V", "bucket", "", "getBucket", "()Ljava/lang/String;", "setBucket", "(Ljava/lang/String;)V", "buildNumber", "getBuildNumber", "setBuildNumber", "description", "getDescription", "setDescription", "hasState", "", "getHasState", "()Z", "setHasState", "(Z)V", "id", "getId", "setId", "lastUpdate", "", "getLastUpdate", "()D", "setLastUpdate", "(D)V", "name", "getName", "setName", "packageName", "getPackageName", "setPackageName", "thumbnail", "getThumbnail", "setThumbnail", "title", "getTitle", "setTitle", "urlAndroid", "getUrlAndroid", "setUrlAndroid", "urlIos", "getUrlIos", "setUrlIos", "version", "getVersion", "setVersion", "website", "getWebsite", "setWebsite", "Companion", "app_debug"})
public class AppInfo implements io.realm.kotlin.types.RealmObject {
    @org.jetbrains.annotations.NotNull()
    public static final com.alva.codedelaroute.models.AppInfo.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    @io.realm.kotlin.types.annotations.PrimaryKey()
    private java.lang.String id = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String name = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String title = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String description = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String buildNumber = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String packageName = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String bucket = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String website = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String urlAndroid = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String urlIos = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String thumbnail = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String version = "";
    private boolean hasState = false;
    private double lastUpdate = 0.0;
    
    public AppInfo() {
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
    public final java.lang.String getName() {
        return null;
    }
    
    public final void setName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitle() {
        return null;
    }
    
    public final void setTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    public final void setDescription(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBuildNumber() {
        return null;
    }
    
    public final void setBuildNumber(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPackageName() {
        return null;
    }
    
    public final void setPackageName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBucket() {
        return null;
    }
    
    public final void setBucket(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getWebsite() {
        return null;
    }
    
    public final void setWebsite(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUrlAndroid() {
        return null;
    }
    
    public final void setUrlAndroid(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUrlIos() {
        return null;
    }
    
    public final void setUrlIos(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getThumbnail() {
        return null;
    }
    
    public final void setThumbnail(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getVersion() {
        return null;
    }
    
    public final void setVersion(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final boolean getHasState() {
        return false;
    }
    
    public final void setHasState(boolean p0) {
    }
    
    public final double getLastUpdate() {
        return 0.0;
    }
    
    public final void setLastUpdate(double p0) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\t\u0010\u0003\u001a\u00020\u0001H\u00d6\u0001J\t\u0010\u0004\u001a\u00020\u0001H\u00d6\u0001\u00a8\u0006\u0005"}, d2 = {"Lcom/alva/codedelaroute/models/AppInfo$Companion;", "", "()V", "io_realm_kotlin_newInstance", "io_realm_kotlin_schema", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}