package com.example.mybaseprojectwithhilt.di

import android.content.Context
import androidx.room.Room
import com.example.mybaseprojectwithhilt.BuildConfig
import com.example.mybaseprojectwithhilt.data.source.DefaultRepository
import com.example.mybaseprojectwithhilt.data.source.local.LocalDataSource
import com.example.mybaseprojectwithhilt.data.source.local.MyDatabase
import com.example.mybaseprojectwithhilt.data.source.remote.RemoteDataSource
import com.example.mybaseprojectwithhilt.data.source.remote.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService,ioDispatcher: CoroutineDispatcher): RemoteDataSource {
        return RemoteDataSource(apiService,ioDispatcher)
    }
    @Singleton
    @Provides
    fun provideTasksLocalDataSource(
        database: MyDatabase,
        ioDispatcher: CoroutineDispatcher
    ):LocalDataSource {
        return LocalDataSource(
            database.countryDao(), ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): MyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MyDatabase::class.java,
            "Tasks.db"
        ).build()
    }
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{
    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        ioDispatcher: CoroutineDispatcher
    ): DefaultRepository {
        return DefaultRepository(
            remoteDataSource, localDataSource,ioDispatcher
        )
    }
}