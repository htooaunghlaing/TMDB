package com.app.tmdb.di

import android.app.Application
import androidx.room.Room
import com.app.tmdb.api.TMDPApi
import com.app.tmdb.app.TMDBApp
import com.app.tmdb.data.db.TMDBDatabase
import com.app.tmdb.util.ConnectionLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import retrofit2.Retrofit.Builder

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (com.app.tmdb.BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()


    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Builder().client(okHttpClient).baseUrl(com.app.tmdb.BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesTMDBAPI(retrofit: Retrofit): TMDPApi = retrofit.create(
        TMDPApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app : Application) : TMDBDatabase =
        Room.databaseBuilder(app, TMDBDatabase::class.java, "tmdb_database")
            .build()

    @Provides
    @Singleton
    fun provideConnectionLiveData(app : Application) : ConnectionLiveData =
       ConnectionLiveData(app)

    @Provides
    @Singleton
    fun provideTMDPApp() : TMDBApp =
        TMDBApp()
}