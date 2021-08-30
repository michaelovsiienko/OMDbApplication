package com.testapp.omdbtestapplication.di

import android.content.Context
import androidx.room.Room
import com.testapp.omdbtestapplication.BuildConfig
import com.testapp.omdbtestapplication.local.OmdbDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideOmdbDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, OmdbDatabase::class.java, BuildConfig.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideOmdbDao(db: OmdbDatabase) = db.getOmdbDao()

}