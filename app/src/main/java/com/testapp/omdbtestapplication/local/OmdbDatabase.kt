package com.testapp.omdbtestapplication.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.testapp.omdbtestapplication.BuildConfig

@Database(
    entities = [OmdbMovieEntity::class],
    version = BuildConfig.DB_VERSION
)
abstract class OmdbDatabase : RoomDatabase() {

    abstract fun getOmdbDao(): OmdbDao
}