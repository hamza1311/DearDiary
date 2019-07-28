package com.hamza.deardiary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.hamza.deardiary.R
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.ui.viewmodels.DiaryItemViewModel
import com.hamza.deardiary.ui.viewmodels.ItemsTagViewModel
import com.hamza.deardiary.util.obtainDiaryItemViewModel
import com.hamza.deardiary.util.obtainItemTagViewModel
import com.hamza.deardiary.util.setBackPressToPopNavigationBackStackFragment
import kotlinx.android.synthetic.main.fragment_item_details.*

class ItemDetailsFragment : Fragment() {
    private lateinit var itemTagViewModel: ItemsTagViewModel
    private lateinit var diaryItemViewModel: DiaryItemViewModel
    private lateinit var currentItem: DiaryItem
    private val args: ItemDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id

        itemTagViewModel = obtainItemTagViewModel(ItemsTagViewModel::class.java)
        diaryItemViewModel = obtainDiaryItemViewModel(DiaryItemViewModel::class.java)

        // Get all the tags
        itemTagViewModel.allItems.observe(this, Observer { list ->
            // Create a new list with the tag names
            val tagsList = mutableListOf<String>()
            list.forEach { tagsList.add(it.tagName) }
            // Set up the ArrayAdapter to display the tags
            val arrayAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, tagsList)

            // Get the item whose details we are viewing
            diaryItemViewModel.get(id).observe(this@ItemDetailsFragment, Observer {
                currentItem = it
                // Set the tag of the item on the spinner
                itemDetails_tags_spinner.setSelection(
                    arrayAdapter.getPosition(it.tag)
                )

                // show the time created
                itemDetails_time_textView.text = DiaryItem.formatTime(it.timeCreated)
            })

            itemDetails_tags_spinner.apply {
                // Setup the spinner
                adapter = arrayAdapter
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        if (tagsList.size == 1) {
                            currentItem.tag = tagsList[0]
                            diaryItemViewModel.update(currentItem)
                        }
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        currentItem.tag = tagsList[position]
                        diaryItemViewModel.update(currentItem)

                    }
                }
            }
        })

        view.setBackPressToPopNavigationBackStackFragment()
    }
}
