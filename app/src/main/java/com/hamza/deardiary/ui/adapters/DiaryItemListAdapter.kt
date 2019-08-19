package com.hamza.deardiary.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamza.deardiary.R
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.ui.activities.NewUpdateOrViewDiaryItemActivity
import kotlinx.android.synthetic.main.list_recycler_view_item.view.*

class DiaryItemListAdapter(val context: Context) :
    RecyclerView.Adapter<DiaryItemListAdapter.ViewHolder>() {
    var list: List<DiaryItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    internal fun setItems(items: List<DiaryItem>) {
        this.list = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(diaryItem: DiaryItem) {
            itemView.list_title_textView.text = diaryItem.title
            itemView.list_body_textView.text = diaryItem.text.replace("\n\n", " ")
            itemView.list_tag_textView.text = diaryItem.tag

            itemView.setOnClickListener {
                context.startActivity(
                    Intent(context, NewUpdateOrViewDiaryItemActivity::class.java).putExtra(
                        "id",
                        diaryItem.id
                    )
                )
            }

            itemView.setOnLongClickListener {
                // TODO: Show delete confirmation and delete
                true
            }
        }
    }
}