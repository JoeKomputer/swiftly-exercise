package com.example.swiftlylist.ui.main.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.swiftlylist.data.Identifiable


class RecyclerViewAdapter<T>(private val viewTypeProvider: ViewTypeProvider<T>,
                             private val itemResId : Int,
                             private val measureById : Int? = null,
                             private val onRecyclerItemLoaded: OnRecyclerItemLoaded<T>?= null) : RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder<T>>(),
    IRecyclerViewAdapter<T>
where T : Identifiable {

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override var itemList : List<T> = listOf()
        set(value) {
            val diff = DiffUtil.calculateDiff(ListDiffCallback(itemList, value), true)
            field = value
            diff.dispatchUpdatesTo(this)
        }
    override var canvasUnit: Int = 4

    class ListDiffCallback<T>(val old: List<T>, val updated: List<T>) : DiffUtil.Callback() where T : Identifiable {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition].isEquivalent(updated[newItemPosition])
        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = updated.size
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition] == updated[newItemPosition]
    }

    // Must call setupOnScrollListener before attempting to use the onScrollListener
    val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            val firstVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition()
            if (!swipeRefreshLayout.isRefreshing && !itemList.isEmpty()) {
                swipeRefreshLayout.isEnabled = (firstVisiblePosition == 0)
            }
        }
    }

    fun setupOnScrollListener(layoutMgr: LinearLayoutManager, swiper: SwipeRefreshLayout) {
        layoutManager = layoutMgr
        swipeRefreshLayout = swiper
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return CustomViewHolder(view, itemResId, onRecyclerItemLoaded, canvasUnit, measureById)
    }

    override fun getItemViewType(position: Int): Int = viewTypeProvider(itemList[position])

    override fun onBindViewHolder(holder: CustomViewHolder<T>, position: Int) {
        holder.item = itemList[position]
    }

    override fun getItemCount(): Int = itemList.size

    class CustomViewHolder<T>(itemView: View,
                              private val itemResId: Int,
                              private val onRecyclerItemLoaded: OnRecyclerItemLoaded<T>?,
                              private val canvasUnit : Int,
                              private val measureById: Int?) : RecyclerView.ViewHolder(itemView) {
        private val binding: ViewDataBinding? = try { DataBindingUtil.bind(itemView) } catch(t: Throwable) { null }

        private var _item: T? = null
        var item
            set(value) {
                _item = value
                onRecyclerItemLoaded?.invoke(value, itemView)
                if(measureById != null){
                    binding?.setVariable(measureById, canvasUnit)
                }
                binding?.setVariable(itemResId, value)
                binding?.executePendingBindings()
            }
            get() = _item
    }
}

typealias OnRecyclerItemLoaded<T> = (T?, View) -> Unit
typealias ViewTypeProvider<T> = (T) -> Int
