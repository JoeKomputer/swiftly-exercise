package com.example.swiftlylist.ui.main.util

interface IRecyclerViewAdapter<T> {
    var itemList : List<T>
    var canvasUnit : Int
}