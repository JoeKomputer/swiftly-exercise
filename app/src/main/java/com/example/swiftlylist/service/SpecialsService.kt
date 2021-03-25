package com.example.swiftlylist.service

import com.example.swiftlylist.data.Specials
import retrofit2.Response
import retrofit2.http.GET

interface SpecialsService {
    @GET("code-exercise-android/master/backup")
    suspend fun getProducts() : Response<Specials>
}