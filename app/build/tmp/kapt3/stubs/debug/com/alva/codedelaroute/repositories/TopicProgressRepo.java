package com.alva.codedelaroute.repositories;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ!\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0013"}, d2 = {"Lcom/alva/codedelaroute/repositories/TopicProgressRepo;", "", "realm", "Lio/realm/kotlin/Realm;", "(Lio/realm/kotlin/Realm;)V", "getRealm", "()Lio/realm/kotlin/Realm;", "addOrUpdateTopicProgressToRepo", "", "topicProgress", "Lcom/alva/codedelaroute/models/TopicProgress;", "(Lcom/alva/codedelaroute/models/TopicProgress;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearSubTopicProgressData", "subTopicId", "", "parentTopicId", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTopicProgressByTopicId", "topicId", "app_debug"})
public final class TopicProgressRepo {
    @org.jetbrains.annotations.NotNull()
    private final io.realm.kotlin.Realm realm = null;
    
    public TopicProgressRepo(@org.jetbrains.annotations.NotNull()
    io.realm.kotlin.Realm realm) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.realm.kotlin.Realm getRealm() {
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