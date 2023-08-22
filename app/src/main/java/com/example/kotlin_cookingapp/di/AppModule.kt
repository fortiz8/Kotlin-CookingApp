package com.example.kotlin_cookingapp.di

import android.content.Context
import com.example.kotlin_cookingapp.application.KotlinCookingAppApplication
import com.example.kotlin_cookingapp.data.remote.api.Api
import com.example.kotlin_cookingapp.data.remote.api.ApiBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// this tells hilt how to provide dependencies. If we are dealing with an interface with dependencies
// we need to create a module
// In hilt all bindings are in scopes. Hilt will create a new instance of the needed type
// everytime our application requests the bindings.
// We need to create the application and instantiate it once using Singleton
// This object is used to set up dependency injection
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesCookingAppApplication(
        @ApplicationContext app: Context
    ): KotlinCookingAppApplication{
        return app as KotlinCookingAppApplication
    }

    // Tells hilt how to provide api.
    // function returns the apiBuilder created
    @Singleton
    @Provides
    fun providesApi(apiBuilder: ApiBuilder): Api {
        return apiBuilder.builder(Api::class.java)
    }
}