package com.privatemessenger.app.retrofit.di

import com.privatemessenger.app.BuildConfig
import com.privatemessenger.app.retrofit.api.ServerApi
import com.privatemessenger.app.retrofit.interceptor.AuthInterceptor
import com.privatemessenger.app.retrofit.interceptor.InternetInterceptor
import com.privatemessenger.app.retrofit.service.InternetConnectionService
import com.privatemessenger.app.retrofit.service.InternetConnectionServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val TIMEOUT_FOR_REQUEST = 120L

    @Provides
    fun createServerApi(
        okHttpClient: OkHttpClient,
    ): ServerApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BACKEND_URL)
            .client(okHttpClient)
            .build()
            .create(ServerApi::class.java)
    }

    @Provides
    fun createHttpClient(
        internetInterceptor: InternetInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()

        httpClientBuilder.readTimeout(TIMEOUT_FOR_REQUEST, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(TIMEOUT_FOR_REQUEST, TimeUnit.SECONDS)

        httpClientBuilder.addInterceptor(internetInterceptor)
        httpClientBuilder.addInterceptor(authInterceptor)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(logging)
        }

        return httpClientBuilder.build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class InternetConnectionServiceModule {
    @Binds
    abstract fun bindInternetConnectionService(
        internetConnectionServiceImpl: InternetConnectionServiceImpl
    ): InternetConnectionService
}


