package com.example.lab1.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\u0016\u001a\u00020\u0017H\u0096@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000fH\u0096@\u00a2\u0006\u0002\u0010\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0011X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/example/lab1/data/repository/MessageRepositoryImpl;", "Lcom/example/lab1/domain/repository/MessageRepository;", "api", "Lcom/example/lab1/data/remote/api/DummyJsonApi;", "messageDao", "Lcom/example/lab1/data/local/dao/MessageDao;", "likesStorage", "Lcom/example/lab1/data/local/LikesStorage;", "paginationManager", "Lcom/example/lab1/data/remote/pagination/PaginationManager;", "ioDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Lcom/example/lab1/data/remote/api/DummyJsonApi;Lcom/example/lab1/data/local/dao/MessageDao;Lcom/example/lab1/data/local/LikesStorage;Lcom/example/lab1/data/remote/pagination/PaginationManager;Lkotlinx/coroutines/CoroutineDispatcher;)V", "likesChangeCounter", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "messages", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/lab1/domain/model/Message;", "getMessages", "()Lkotlinx/coroutines/flow/Flow;", "refreshMessages", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toggleLike", "", "messageId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MessageRepositoryImpl implements com.example.lab1.domain.repository.MessageRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.lab1.data.remote.api.DummyJsonApi api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.lab1.data.local.dao.MessageDao messageDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.lab1.data.local.LikesStorage likesStorage = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.lab1.data.remote.pagination.PaginationManager paginationManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineDispatcher ioDispatcher = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> likesChangeCounter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.example.lab1.domain.model.Message>> messages = null;
    
    public MessageRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.example.lab1.data.remote.api.DummyJsonApi api, @org.jetbrains.annotations.NotNull()
    com.example.lab1.data.local.dao.MessageDao messageDao, @org.jetbrains.annotations.NotNull()
    com.example.lab1.data.local.LikesStorage likesStorage, @org.jetbrains.annotations.NotNull()
    com.example.lab1.data.remote.pagination.PaginationManager paginationManager, @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineDispatcher ioDispatcher) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.lab1.domain.model.Message>> getMessages() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object refreshMessages(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object toggleLike(long messageId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}