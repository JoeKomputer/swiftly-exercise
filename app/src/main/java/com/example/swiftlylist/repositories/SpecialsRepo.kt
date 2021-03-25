package com.example.swiftlylist.repositories

import com.example.swiftlylist.service.SpecialsService
import javax.inject.Inject

class SpecialsRepo @Inject constructor(
    private val specialsService: SpecialsService
) {
    var client: SpecialsService = specialsService

    suspend fun getProducts() = client.getProducts()
}