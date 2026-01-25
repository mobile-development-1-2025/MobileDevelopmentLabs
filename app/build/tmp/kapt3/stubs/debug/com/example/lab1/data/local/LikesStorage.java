package com.example.lab1.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\fJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/example/lab1/data/local/LikesStorage;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "getKey", "", "messageId", "", "getLikedMessageIds", "", "isLiked", "", "toggleLike", "Companion", "app_debug"})
public final class LikesStorage {
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREF_NAME = "likes_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PREFIX = "message_liked_";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.lab1.data.local.LikesStorage.Companion Companion = null;
    
    public LikesStorage(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final boolean isLiked(long messageId) {
        return false;
    }
    
    public final boolean toggleLike(long messageId) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.Long> getLikedMessageIds() {
        return null;
    }
    
    private final java.lang.String getKey(long messageId) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/example/lab1/data/local/LikesStorage$Companion;", "", "()V", "KEY_PREFIX", "", "PREF_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}