package ru.itmo.mobiledev.lab3;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0012\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bJ\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0010"}, d2 = {"Lru/itmo/mobiledev/lab3/MessageRepository;", "", "api", "Lru/itmo/mobiledev/lab3/MessageApi;", "dao", "Lru/itmo/mobiledev/lab3/MessageDao;", "(Lru/itmo/mobiledev/lab3/MessageApi;Lru/itmo/mobiledev/lab3/MessageDao;)V", "observeMessages", "Lkotlinx/coroutines/flow/Flow;", "", "Lru/itmo/mobiledev/lab3/MessageEntity;", "refresh", "Lkotlin/Result;", "", "refresh-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MessageRepository {
    @org.jetbrains.annotations.NotNull()
    private final ru.itmo.mobiledev.lab3.MessageApi api = null;
    @org.jetbrains.annotations.NotNull()
    private final ru.itmo.mobiledev.lab3.MessageDao dao = null;
    
    public MessageRepository(@org.jetbrains.annotations.NotNull()
    ru.itmo.mobiledev.lab3.MessageApi api, @org.jetbrains.annotations.NotNull()
    ru.itmo.mobiledev.lab3.MessageDao dao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<ru.itmo.mobiledev.lab3.MessageEntity>> observeMessages() {
        return null;
    }
}