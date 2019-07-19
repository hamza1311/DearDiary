package com.hamza.deardiary.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hamza.deardiary.R
import com.hamza.deardiary.ui.activities.NewUpdateOrViewDiaryItemActivity
import com.hamza.deardiary.ui.adapters.DiaryItemListAdapter
import com.hamza.deardiary.ui.viewmodels.DiaryItemViewModel
import com.hamza.deardiary.util.obtainDiaryItemViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import android.graphics.*
import kotlin.math.roundToInt

class ItemsListFragment : Fragment() {
    private lateinit var viewModel: DiaryItemViewModel
    private lateinit var listAdapter: DiaryItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainDiaryItemViewModel(DiaryItemViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = DiaryItemListAdapter(context = context!!)
        list_recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        newItem_fab.setOnClickListener {
            startActivity(Intent(context, NewUpdateOrViewDiaryItemActivity::class.java).putExtra("id", 0))
        }

        viewModel.allItems.observe(this, Observer { items ->
            listAdapter.setItems(items)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.delete(listAdapter.list[viewHolder.adapterPosition].id)
                listAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                val trashBinIcon = resources.getDrawable(
                    R.drawable.ic_delete_black_24dp,
                    null
                )
                c.clipRect(
                    0f, viewHolder.itemView.top.toFloat(),
                    dX, viewHolder.itemView.bottom.toFloat()
                )
                c.drawColor(Color.RED) // TODO: Replace `Color.RED` with a @color res

                val buttonWidth = resources.getDimension(R.dimen.delete_ic_size).roundToInt()
                val itemView = viewHolder.itemView
                trashBinIcon.bounds =
                    Rect(itemView.left, itemView.top, itemView.left * (buttonWidth / 2), itemView.bottom)
                trashBinIcon.draw(c)
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }).attachToRecyclerView(list_recyclerView)
    }
}
