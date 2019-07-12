package com.hamza.deardiary.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.hamza.deardiary.R
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.ui.viewmodels.DiaryItemViewModel
import com.hamza.deardiary.util.obtainDiaryItemViewModel
import kotlinx.android.synthetic.main.fragment_add_view_update_item.*

class AddViewUpdateItemFragment : Fragment() {
    private var currentId: Int = 0
    private lateinit var viewModel: DiaryItemViewModel
    private lateinit var currentItem: DiaryItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_view_update_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentId = activity?.intent?.extras?.getInt("id") ?: 0
//        viewModel = ViewModelProviders.of(this).get(DiaryItemViewModel::class.java)
        viewModel = obtainDiaryItemViewModel(DiaryItemViewModel::class.java)

        if (currentId != 0) {
            viewModel.get(currentId).observe(this, Observer {
                currentItem = it
                newOrEditAct_title_editText.setText(it.title)
                newOrEditAct_body_editText.setText(it.text)
                time_textView.text = DiaryItem.formatTime(it.timeCreated)
                newOrEditAct_tags_textView.text = it.tag
                setTitleAndBody()
                if (currentItem.isLocked) {
                    lockOrUnlockItem(false)
                }
            })
        } else {
            currentItem = DiaryItem(title = "title", text = "text", tag = "")
            setTitleAndBody()
        }
    }

    override fun onPause() {
        super.onPause()
        saveOrUpdate()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_new_update_or_view, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_lockItem_menuItem -> {
                if (!currentItem.isLocked) {
                    currentItem.isLocked = true
                    viewModel.update(currentItem)
                    lockOrUnlockItem(false)
                } else {
                    currentItem.isLocked = false
                    viewModel.update(currentItem)
                    lockOrUnlockItem(true)
                }
                true
            }
            R.id.action_details_menuItem -> {
                if (currentId != 0) {
                    saveOrUpdate()
                    findNavController().navigate(
                        AddViewUpdateItemFragmentDirections.actionToItemDetailsFragment(
                            currentId
                        )
                    )
                } else {
//                    TODO: Add a save fab and make a toast saying save before checking details
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun lockOrUnlockItem(status: Boolean) {
        newOrEditAct_title_editText.isEnabled = status
        newOrEditAct_body_editText.isEnabled = status
        newOrEditAct_tags_textView.isEnabled = status
    }

    private fun setTitleAndBody() {
        currentItem.text = newOrEditAct_body_editText.text.toString()
        currentItem.title = newOrEditAct_title_editText.text.toString()
    }

    private fun saveOrUpdate() {
        setTitleAndBody()
        if (currentId == 0) {
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
            viewModel.insert(currentItem)
        } else {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
            viewModel.update(currentItem)
        }
    }
}
