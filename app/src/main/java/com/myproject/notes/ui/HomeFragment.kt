package com.myproject.notes.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.myproject.notes.adapter.ManageClicks
import com.myproject.notes.adapter.NotesAdapter
import com.myproject.notes.R
import com.myproject.notes.databinding.FragmentHomeBinding
import com.myproject.notes.model.NotesModel
import com.myproject.notes.viewModel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), ManageClicks {
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        viewModel.data.observe(requireActivity()) {
            binding.notesRecycler.setHasFixedSize(true)
            binding.notesRecycler.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            val myAdapter = NotesAdapter(it, this)
            binding.notesRecycler.adapter = myAdapter

            if (myAdapter.itemCount == 0) {
                binding.notesRecycler.visibility = View.GONE
                binding.noDataTv.visibility = View.VISIBLE
            } else {
                binding.noDataTv.visibility = View.GONE
                binding.notesRecycler.visibility = View.VISIBLE
            }
        }

        binding.addBtn.setOnClickListener { addButton() }

        return binding.root
    }

    @SuppressLint("ServiceCast")
    private fun addButton() {
        val vib = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        if (vib != null) {
            if (Build.VERSION.SDK_INT >= 26) {
                vib.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vib.vibrate(50)
            }
        }
        binding.root.findNavController().navigate(R.id.action_homeFragment_to_editFragment)
    }

    override fun click(notesModel: NotesModel) {
        val bundle = bundleOf(
            "NotesModel" to NotesModel(
                notesModel.id,
                notesModel.title,
                notesModel.note,
                notesModel.date
            )
        )
        findNavController().navigate(R.id.action_homeFragment_to_editFragment, bundle)

    }

    override fun longClick(id: Long) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Delete Note")
        dialogBuilder.setMessage("Are you sure you want to delete this note?")
        dialogBuilder.setIcon(R.drawable.delete)
        dialogBuilder.setPositiveButton("Delete") { it, _ ->
            viewModel.deleteData(id)
            it.dismiss()
        }
        dialogBuilder.setNegativeButton("Cancel") { it, _ ->
            it.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }


}