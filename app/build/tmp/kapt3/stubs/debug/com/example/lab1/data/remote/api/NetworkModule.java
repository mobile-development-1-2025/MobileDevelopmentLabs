package com.example.lab1.data.remote.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\n\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\n\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0017"}, d2 = {"Lcom/example/lab1/data/remote/api/NetworkModule;", "", "()V", "BASE_URL", "", "api", "Lcom/example/lab1/data/remote/api/DummyJsonApi;", "getApi", "()Lcom/example/lab1/data/remote/api/DummyJsonApi;", "api$delegate", "Lkotlin/Lazy;", "loggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "okHttpClient", "Lokhttp3/OkHttpClient;", "getOkHttpClient", "()Lokhttp3/OkHttpClient;", "okHttpClient$delegate", "retrofit", "Lretrofit2/Retrofit;", "getRetrofit", "()Lretrofit2/Retrofit;", "retrofit$delegate", "app_debug"})
public final class NetworkModule {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BASE_URL = "https://dummyjson.com/";
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.logging.HttpLoggingInterceptor loggingInterceptor = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy okHttpClient$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy retrofit$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy api$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.lab1.data.remote.api.NetworkModule INSTANCE = null;
    
    private NetworkModule() {
        super();
    }
    
    private final okhttp3.OkHttpClient getOkHttpClient() {
        return null;
    }
    
    private final retrofit2.Retrofit getRetrofit() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.lab1.data.remote.api.DummyJsonApi getApi() {
        return null;
    }
}