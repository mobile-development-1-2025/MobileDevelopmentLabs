package com.example.lab1.work;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0002J\u000e\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/example/lab1/work/WorkManagerInitializer;", "", "()V", "REPEAT_INTERVAL", "", "SYNC_WORK_NAME", "", "initialize", "", "context", "Landroid/content/Context;", "scheduleFirstWork", "constraints", "Landroidx/work/Constraints;", "scheduleNextWork", "app_debug"})
public final class WorkManagerInitializer {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String SYNC_WORK_NAME = "sync_messages_work";
    private static final long REPEAT_INTERVAL = 1L;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.lab1.work.WorkManagerInitializer INSTANCE = null;
    
    private WorkManagerInitializer() {
        super();
    }
    
    public final void initialize(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    private final void scheduleFirstWork(android.content.Context context, androidx.work.Constraints constraints) {
    }
    
    public final void scheduleNextWork(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
}