package com.example.swiftlylist.ui.main.util
import android.graphics.Paint
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swiftlylist.data.Identifiable
import com.google.android.material.card.MaterialCardView

object BindingAdapters {
    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    @BindingAdapter(value = ["items", "canvasUnit", "autoscroll"], requireAll = false)
    fun <T> setRecyclerViewItems(recyclerView: RecyclerView,
                                 items: List<T>?,
                                 canvasUnit: Int?,
                                 autoscroll: Boolean?,
                        ) where T : Identifiable {
        val recyclerAdapter = recyclerView.adapter as? IRecyclerViewAdapter<T>
        if (recyclerAdapter != null) {
            recyclerAdapter.canvasUnit = canvasUnit?:4
            recyclerAdapter.itemList = items ?: listOf()
        }
        if (autoscroll == true && items != null) {
            recyclerView.scrollToPosition(0)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["canvasUnit", "widthUnit", "heightUnit"], requireAll = false)
    fun setCardSize(cardView: MaterialCardView, canvasUnit : Int, widthUnit : Int, heightUnit : Int) {
        val deviceWidth = cardView.resources.displayMetrics.widthPixels
        val multiplier = deviceWidth/canvasUnit
        cardView.layoutParams.height = heightUnit * multiplier
        cardView.layoutParams.width = widthUnit * multiplier
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .into(view)
    }


    @JvmStatic
    @BindingAdapter("strikethrough")
    fun strikeText(view: TextView, show: Boolean) {
        view.paintFlags = if (show) {
            view.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            view.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    @JvmStatic
    @BindingAdapter("textCurrency")
    fun setCurrency(view: TextView, text: String) {
        view.text = "\$$text"
    }
}