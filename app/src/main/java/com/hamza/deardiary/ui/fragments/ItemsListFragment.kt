package com.hamza.deardiary.ui.fragments

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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
import kotlin.math.roundToInt

/**
 * Fragment for showing all the items. This is one of the 2 fragments shown in viewpager in [MainFragment]
 */
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
        // Setup recycler view
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

        // Set the ItemTouchHelper to handle swipe event on recycler view.
        // Swipe right is delete
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

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                // Get the trashbin drawable
                val trashBinIcon = resources.getDrawable(
                    R.drawable.ic_delete_black_24dp,
                    null
                )
                c.clipRect(
                    0f, viewHolder.itemView.top.toFloat(),
                    dX, viewHolder.itemView.bottom.toFloat()
                )
                // Get color resource and draw it
                c.drawColor(resources.getColor(R.color.colorSwipeToDeleteBg, null))

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
