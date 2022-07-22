package com.alva.codedelaroute;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/alva/codedelaroute/CodeDeLaRouteApplication;", "Landroid/app/Application;", "()V", "REALM_SCHEMA_VERSION", "", "copyBundledRealmFile", "", "inputStream", "Ljava/io/InputStream;", "outFileName", "onCreate", "", "Companion", "app_debug"})
public final class CodeDeLaRouteApplication extends android.app.Application {
    private final long REALM_SCHEMA_VERSION = 1L;
    @org.jetbrains.annotations.NotNull()
    public static final com.alva.codedelaroute.CodeDeLaRouteApplication.Companion Companion = null;
    public static io.realm.kotlin.Realm realm;
    
    public CodeDeLaRouteApplication() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    private final java.lang.String copyBundledRealmFile(java.io.InputStream inputStream, java.lang.String outFileName) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/alva/codedelaroute/CodeDeLaRouteApplication$Companion;", "", "()V", "realm", "Lio/realm/kotlin/Realm;", "getRealm", "()Lio/realm/kotlin/Realm;", "setRealm", "(Lio/realm/kotlin/Realm;)V", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final io.realm.kotlin.Realm getRealm() {
            return null;
        }
        
        public final void setRealm(@org.jetbrains.annotations.NotNull()
        io.realm.kotlin.Realm p0) {
        }
    }
}