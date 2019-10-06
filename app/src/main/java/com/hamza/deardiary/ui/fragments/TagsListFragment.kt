package com.hamza.deardiary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamza.deardiary.R
import com.hamza.deardiary.ui.adapters.ItemsTagListAdapter
import com.hamza.deardiary.ui.viewmodels.ItemsTagViewModel
import com.hamza.deardiary.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_tags_list.*

class TagsListFragment : Fragment() {
    private lateinit var viewModel: ItemsTagViewModel
    private lateinit var listAdapter: ItemsTagListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tags_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = obtainViewModel(ItemsTagViewModel::class.java)

        listAdapter = ItemsTagListAdapter(context!!)
        tagsList_fab.setOnClickListener {
            it.findNavController().navigate(R.id.action_toNewTagFragment)
        }

        viewModel.allItems.observe(this, Observer { items ->
            listAdapter.setItems(items)
        })

        tagsList_recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
