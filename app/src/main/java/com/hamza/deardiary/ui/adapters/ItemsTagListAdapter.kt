package com.hamza.deardiary.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hamza.deardiary.R
import com.hamza.deardiary.arch.models.ItemTag
import com.hamza.deardiary.ui.fragments.TagsListFragmentDirections
import kotlinx.android.synthetic.main.tags_list_recycler_view_item.view.*

class ItemsTagListAdapter(val context: Context) : RecyclerView.Adapter<ItemsTagListAdapter.ViewHolder>() {
    var list: List<ItemTag> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.tags_list_recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(list[position])
    }

    internal fun setItems(items: List<ItemTag>) {
        this.list = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViews(item: ItemTag) {
            itemView.tagsList_tagName_textView.text = item.tagName
            ImageViewCompat.setImageTintList(itemView.tagsList_tag_imageView, ColorStateList.valueOf(item.tagColor))

            itemView.setOnClickListener {
                val action = TagsListFragmentDirections.actionTagsListFragmentToTagItemsFragment(item.id)
                it.findNavController().navigate(action)
            }
        }
    }

}