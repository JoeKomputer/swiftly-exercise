package com.example.swiftlylist.data

data class ManagerSpecial(
    val display_name: String,
    val height: Int,
    val imageUrl: String,
    val original_price: String,
    val price: String,
    val width: Int
) : Identifiable {
    //create something unique to identify them by
    override val id: String
        get() = display_name + imageUrl
}