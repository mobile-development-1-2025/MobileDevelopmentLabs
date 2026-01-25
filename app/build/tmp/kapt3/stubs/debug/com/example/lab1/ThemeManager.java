package com.example.lab1;

/**
 * Simple helper that stores current theme choice and applies it when needed.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0018\u0010\n\u001a\n \f*\u0004\u0018\u00010\u000b0\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\u000f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u000eJ\u0010\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/example/lab1/ThemeManager;", "", "()V", "KEY_DARK_THEME", "", "PREFS_NAME", "applySavedTheme", "", "context", "Landroid/content/Context;", "getPrefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "isDarkThemeEnabled", "", "saveAndApply", "enableDarkTheme", "setNightMode", "app_debug"})
public final class ThemeManager {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "app_settings";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DARK_THEME = "dark_theme_enabled";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.lab1.ThemeManager INSTANCE = null;
    
    private ThemeManager() {
        super();
    }
    
    public final void applySavedTheme(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final boolean isDarkThemeEnabled(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void saveAndApply(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean enableDarkTheme) {
    }
    
    private final android.content.SharedPreferences getPrefs(android.content.Context context) {
        return null;
    }
    
    private final void setNightMode(boolean enableDarkTheme) {
    }
}