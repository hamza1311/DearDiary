package com.hamza.deardiary.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.hamza.deardiary.R
import com.hamza.deardiary.arch.models.DiaryItem
import com.hamza.deardiary.ui.viewmodels.DiaryItemViewModel
import com.hamza.deardiary.util.obtainDiaryItemViewModel
import kotlinx.android.synthetic.main.fragment_add_view_update_item.*

/**
 * Fragment for creating, editing and viewing items
 * @property[currentId] the id of current item. 0 means new item
 * @property[currentItem] the item being edited or created
 */
class AddViewUpdateItemFragment : Fragment() {
    private var currentId: Int = 0
    private lateinit var viewModel: DiaryItemViewModel
    private lateinit var currentItem: DiaryItem
    private val sharedPrefs by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

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
        // The id passed is 0 for a new item
        currentId = activity?.intent?.extras?.getInt("id") ?: 0
        viewModel = obtainDiaryItemViewModel(DiaryItemViewModel::class.java)

        // Check if a new item is supposed to be created or an existing item is supposed to be edited
        if (currentId != 0) {
            // Now we know that an existing element is to be edited.
            // We ask the view model to give us the item for the id passed
            viewModel.get(currentId).observe(this, Observer {
                currentItem = it
                newOrEdit_title_editText.setText(it.title)
                newOrEdit_body_editText.setText(it.text)
                if (currentItem.isLocked) {
                    updateUIforLockAndUnlockItem(false)
                }
            })
        } else {
            // Here a new is created. This creates a new item and stores it in [currentItem]
            currentItem = DiaryItem(title = "title", text = "text", tag = "")
            setTitleAndBodyOnObject()
        }

        newOrEdit_save_fab.setOnClickListener {
            // Sets an onClickListener for the save fab to execute the save command
            saveOrUpdate()
        }
    }

    override fun onPause() {
        super.onPause()
        // The user has either backed out or left the app.
        // So we save or update the item
        saveOrUpdate()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflates the menu layout and show the menu
        inflater.inflate(R.menu.menu_new_update_or_view, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle click for the menu items
        return when (item.itemId) {
            R.id.action_lockItem_menuItem -> {
                val newItem = viewModel.lockOrUnlockAndReturnItem(currentItem)
                updateUIforLockAndUnlockItem(!newItem.isLocked)
                true
            }
            R.id.action_details_menuItem -> {
                // Check that an existing item is being edited
                if (currentId != 0) {
                    // Now that we know an existing item is being edited, we navigate to ItemDetailsFragment and pass the id
                    saveOrUpdate()
                    findNavController().navigate(
                        AddViewUpdateItemFragmentDirections.actionToItemDetailsFragment(
                            currentId
                        )
                    )
                } else {
                    // This is a new item
                    // Prompt the user to save it before checking the details
                    Toast.makeText(context, getString(R.string.save_before_details), Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.action_hideItem_menuItem -> {
                val newItem = viewModel.hideOrUnhideAndReturnItem(currentItem)
                Toast.makeText(context, if (newItem.isHidden) "Hidden" else "Unhidden", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateUIforLockAndUnlockItem(status: Boolean) {
        newOrEdit_title_editText.isEnabled = status
        newOrEdit_body_editText.isEnabled = status
    }

    private fun setTitleAndBodyOnObject() {
        currentItem.text = newOrEdit_body_editText.text.toString()
        currentItem.title = newOrEdit_title_editText.text.toString()
    }

    private fun saveOrUpdate() {
        setTitleAndBodyOnObject()
        // Check that its a new item
        if (currentId == 0 && !viewModel.getIsSaved()) {
            // This is a new item so we save it
            currentId = viewModel.insert(
                currentItem,
                sharedPrefs.getBoolean("enableSignaturePreference", false),
                sharedPrefs.getString("setSignature", "") ?: ""
            ).toInt().also {
                viewModel.setIsSaved(true)
            }
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
        } else {
            // An existing item is being edited so we update it
            viewModel.update(currentItem)
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
        }
    }
}
