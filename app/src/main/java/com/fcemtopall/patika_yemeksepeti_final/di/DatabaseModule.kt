package com.fcemtopall.patika_yemeksepeti_final.di

import android.content.Context
import com.fcemtopall.patika_yemeksepeti_final.models.local.LocalDataSource
import com.fcemtopall.patika_yemeksepeti_final.models.local.SharedPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(
    ActivityRetainedComponent::class
)
class DatabaseModule {

    @Provides
    fun sharedPrefManager(@ApplicationContext context: Context): SharedPrefManager {
        return SharedPrefManager(context)
    }

    @Provides
    fun localDataSource(sharedPrefManager: SharedPrefManager): LocalDataSource {
        return LocalDataSource(sharedPrefManager)
    }
}