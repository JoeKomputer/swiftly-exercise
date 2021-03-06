package com.example.swiftlylist.data

interface Identifiable {
    val id : String
    fun isEquivalent(e : Identifiable) : Boolean = this.id == e.id
}