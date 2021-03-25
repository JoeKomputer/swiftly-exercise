package com.example.swiftlylist.di

import com.example.swiftlylist.repositories.SpecialsRepo
import com.example.swiftlylist.service.SpecialsService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/Swiftly-Systems/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideProductService(retrofit: Retrofit): SpecialsService = retrofit.create(SpecialsService::class.java)

    @Singleton
    @Provides
    fun provideRepository(specialsService: SpecialsService) = SpecialsRepo(specialsService)
}