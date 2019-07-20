package com.hamza.deardiary.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.hamza.deardiary.R
import com.hamza.deardiary.arch.models.ItemTag
import com.hamza.deardiary.ui.viewmodels.ItemsTagViewModel
import com.hamza.deardiary.util.obtainItemTagViewModel
import com.hamza.deardiary.util.setBackPressToPopNavigationBackStackFragment
import kotlinx.android.synthetic.main.fragment_new_tag.*

class NewTagFragment : Fragment() {
    private val args: NewTagFragmentArgs by navArgs()
    private lateinit var itemsTagViewModel: ItemsTagViewModel
    private var currentTag: ItemTag = ItemTag(tagName = "", tagColor = 0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_tag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newTag_colors_spinner.adapter =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, colors.keys.toList())

        itemsTagViewModel = obtainItemTagViewModel(ItemsTagViewModel::class.java)

        newTag_colors_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentTag.tagColor = colors.getValue(colors.keys.toList()[position])
            }
        }

        newTag_save_button.setOnClickListener {
            if (args.id == 0) {
                currentTag.tagName = newTag_name_editText.text.toString()
                itemsTagViewModel.insert(currentTag)
            }
            it.findNavController().popBackStack()
        }

        view.setBackPressToPopNavigationBackStackFragment()
    }

    companion object {
        private val colors = mapOf(
            "Black" to -16777216,
            "Blue" to -16776961,
            "Cyan" to -16711681,
            "Dark Gray" to -12303292,
            "Gray" to -7829368,
            "Light Gray" to -3355444,
            "Magenta" to -65281,
            "RED" to -65536,
            "White" to -1,
            "Yellow" to -256
        )
    }

}
