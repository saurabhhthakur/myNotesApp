package com.myproject.notes.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.myproject.notes.R
import com.myproject.notes.databinding.FragmentEditBinding
import com.myproject.notes.model.NotesModel
import com.myproject.notes.viewModel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private var obj: NotesModel? = null

    @Inject
    lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
         obj = it.getParcelable("NotesModel")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        if (obj != null) {
            binding.toolbar.title = "Edit Notes..."
            binding.title.setText(obj?.title)
            binding.note.setText(obj?.note)
        } else
            binding.toolbar.title = "Add Notes..."

        binding.done.setOnClickListener {
            doneBtn()
        }

        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    private fun doneBtn() {
        if (binding.title.text.isNotEmpty() || binding.note.text.isNotEmpty()) {
            if (obj != null) {
                obj?.id?.let {
                    viewModel.updateData(
                        it,
                        binding.title.text.toString(),
                        binding.note.text.toString(),
                        SimpleDateFormat("EEE dd MMM").format(Calendar.getInstance().time)
                    )
                }
            } else {
                viewModel.addData(
                    NotesModel(
                        0,
                        binding.title.text.toString(),
                        binding.note.text.toString(),
                        SimpleDateFormat("EEE dd MMM").format(Calendar.getInstance().time)
                    )
                )
            }
            val navController = findNavController()
            navController.navigate(R.id.action_editFragment_to_homeFragment)

        }
    }

}