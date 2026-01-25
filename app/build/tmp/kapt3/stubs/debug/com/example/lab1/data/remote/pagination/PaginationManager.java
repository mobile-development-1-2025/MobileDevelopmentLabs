package com.example.lab1.data.remote.pagination;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\bJ\u0006\u0010\n\u001a\u00020\bJ\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/example/lab1/data/remote/pagination/PaginationManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "getCurrentPage", "", "getNextPage", "getPageSize", "reset", "", "Companion", "app_debug"})
public final class PaginationManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREF_NAME = "pagination_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_CURRENT_PAGE = "current_page";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PAGE_SIZE = "page_size";
    private static final int DEFAULT_PAGE_SIZE = 30;
    private static final int MAX_PAGES = 12;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.lab1.data.remote.pagination.PaginationManager.Companion Companion = null;
    
    public PaginationManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Получает следующую страницу для загрузки
     * Переключается между страницами циклически для проверки обновления
     */
    public final int getNextPage() {
        return 0;
    }
    
    /**
     * Получает текущую страницу
     */
    public final int getCurrentPage() {
        return 0;
    }
    
    /**
     * Получает размер страницы
     */
    public final int getPageSize() {
        return 0;
    }
    
    /**
     * Сбрасывает пагинацию на первую страницу
     */
    public final void reset() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/example/lab1/data/remote/pagination/PaginationManager$Companion;", "", "()V", "DEFAULT_PAGE_SIZE", "", "KEY_CURRENT_PAGE", "", "KEY_PAGE_SIZE", "MAX_PAGES", "PREF_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}