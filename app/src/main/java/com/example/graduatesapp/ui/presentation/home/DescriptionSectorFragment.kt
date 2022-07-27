package com.example.graduatesapp.ui.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.graduatesapp.R
import com.example.graduatesapp.databinding.FragmentDescriptionSectorBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DescriptionSectorFragment : BottomSheetDialogFragment() {
   private lateinit var binding:FragmentDescriptionSectorBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDescriptionSectorBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sector = DescriptionSectorFragmentArgs.fromBundle(requireArguments()).sector
        binding.name.text = sector.name
        binding.description.text = sector.description
        binding.school.text = sector.school
        binding.viewGraduates.setOnClickListener {
            val action = DescriptionSectorFragmentDirections.actionDescriptionSectorFragmentToGraduatesFragment(sector.id)
            findNavController().navigate(action)
        }
    }
}