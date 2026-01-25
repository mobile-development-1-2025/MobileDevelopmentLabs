package com.example.lab1.data.remote.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\b\b\u0003\u0010\u0004\u001a\u00020\u00052\b\b\u0003\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/example/lab1/data/remote/api/DummyJsonApi;", "", "getComments", "Lcom/example/lab1/data/remote/dto/CommentsResponseDto;", "limit", "", "skip", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface DummyJsonApi {
    
    @retrofit2.http.GET(value = "comments")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getComments(@retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "skip")
    int skip, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.lab1.data.remote.dto.CommentsResponseDto> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}