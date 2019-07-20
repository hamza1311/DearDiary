package com.hamza.deardiary.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamza.deardiary.R
import com.hamza.deardiary.ui.adapters.TagItemsListAdapter
import com.hamza.deardiary.ui.viewmodels.DiaryItemViewModel
import com.hamza.deardiary.ui.viewmodels.ItemsTagViewModel
import com.hamza.deardiary.util.obtainDiaryItemViewModel
import com.hamza.deardiary.util.obtainItemTagViewModel
import com.hamza.deardiary.util.setBackPressToPopNavigationBackStackFragment
import kotlinx.android.synthetic.main.fragment_tag_items.*

class TagItemsFragment : Fragment() {
    private val args: TagItemsFragmentArgs by navArgs()
    private lateinit var diaryItemViewModel: DiaryItemViewModel
    private lateinit var itemTagVewModel: ItemsTagViewModel
    private lateinit var listAdapter: TagItemsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(context, "${args.id}", Toast.LENGTH_LONG).show()
        diaryItemViewModel = obtainDiaryItemViewModel(DiaryItemViewModel::class.java)
        itemTagVewModel = obtainItemTagViewModel(ItemsTagViewModel::class.java)
        listAdapter = TagItemsListAdapter(context!!)


        itemTagVewModel.get(args.id).observe(this, Observer { tag ->
            tagItems_tagName_textView.text = tag.tagName
            tagItems_tagName_textView.compoundDrawables.first().setTint(tag.tagColor)
            diaryItemViewModel.getItemsByTag(tag).observe(this, Observer { list ->
                listAdapter.setItems(list)
            })
        })


        tagItems_recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        tagItems_delete_imageButton.setOnClickListener {
            itemTagVewModel.delete(args.id)
            it.findNavController().popBackStack()
        }

        view.setBackPressToPopNavigationBackStackFragment()
    }
}
