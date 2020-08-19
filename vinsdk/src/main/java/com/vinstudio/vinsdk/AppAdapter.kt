package com.vinstudio.vinsdk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vinstudio.vinsdks.App
import kotlinx.android.synthetic.main.app_item.view.*

class AppDiffCallback : DiffUtil.ItemCallback<App>() {
    override fun areItemsTheSame(oldItem: App, newItem: App): Boolean = oldItem.icon == newItem.icon

    override fun areContentsTheSame(oldItem: App, newItem: App): Boolean =
        oldItem.name == newItem.name
}

class AppAdapter : ListAdapter<App, ViewHolder>(AppDiffCallback()) {

    private lateinit var onItemClick: (App) -> Unit

    fun setItemClick(onItemClick: (App) -> Unit) {
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.app_item, parent, false)
        return ViewHolder(inflater).apply { setItemClick(onItemClick) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(getItem(position))
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    protected lateinit var onItemClick: (App) -> Unit
    private var item: App? = null

    fun setItemClick(onItemClick: (App) -> Unit) {
        this.onItemClick = onItemClick
    }

    init {
        itemView.setOnClickListener {
            item?.let {
                onItemClick(it)
            }
        }
    }

    fun bindData(app: App) {
        this.item = app
        Glide.with(itemView.context).load(app.icon).into(itemView.imageViewAppItem)
        itemView.textViewNameAppItem.text = app.name
        itemView.textViewDescriptionAppItem.text = app.description
    }
}
