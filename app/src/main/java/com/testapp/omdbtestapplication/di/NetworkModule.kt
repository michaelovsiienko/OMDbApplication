package com.testapp.omdbtestapplication.di

import com.testapp.omdbtestapplication.BuildConfig
import com.testapp.omdbtestapplication.network.OmdbService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val SERVER_TIMEOUT = 30L

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: LoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(SERVER_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(SERVER_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(SERVER_TIMEOUT, TimeUnit.SECONDS)
        .apply {
            if (BuildConfig.DEBUG) addInterceptor(interceptor)
        }.build()

    @Provides
    fun providePrettyLogger() = LoggingInterceptor.Builder()
        .setLevel(Level.BASIC)
        .log(Platform.INFO)
        .request("Request>>>>>")
        .response("<<<<<Response")
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOmdbService(retrofit: Retrofit): OmdbService {
        return retrofit.create(OmdbService::class.java)
    }
}