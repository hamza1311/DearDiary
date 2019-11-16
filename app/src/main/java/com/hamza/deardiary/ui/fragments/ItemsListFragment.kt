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
import com.hamza.deardiary.util.obtainViewModel
import com.hamza.deardiary.util.setTextColor
import kotlinx.android.synthetic.main.fragment_list.*

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
        setHasOptionsMenu(true)
        viewModel = obtainViewModel(DiaryItemViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setup recycler view
        listAdapter = DiaryItemListAdapter(context = context!!, delete = { viewModel.delete(it) })
        list_recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        newItem_fab.setOnClickListener {
            startActivity(Intent(context, NewUpdateOrViewDiaryItemActivity::class.java).putExtra("id", 0))
        }

        viewModel.getUnhiddenItems().observe(this, Observer { items ->
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
                viewModel.hideItem(listAdapter.list[viewHolder.adapterPosition])
                listAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(list_recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflates the menu layout and show the menu
        inflater.inflate(R.menu.menu_items_list, menu)
        menu.setTextColor(resources)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItemsList_all -> {
                removeObservers()
                viewModel.allItems.observe(this, Observer { items ->
                    listAdapter.setItems(items)
                })
            }
            R.id.menuItemsList_hidden -> {
                removeObservers()
                viewModel.getHiddenItems().observe(this, Observer { items ->
                    listAdapter.setItems(items)
                })
            }
            R.id.menuItemsList_unhidden -> {
                removeObservers()
                viewModel.getUnhiddenItems().observe(this, Observer { items ->
                    listAdapter.setItems(items)
                })
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun removeObservers() {
        viewModel.allItems.removeObservers(this)
        viewModel.getHiddenItems().removeObservers(this)
        viewModel.getUnhiddenItems().removeObservers(this)
    }
}
