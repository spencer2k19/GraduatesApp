package com.example.graduatesapp.ui.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduatesapp.R
import com.example.graduatesapp.databinding.FragmentGraduateDescriptionBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GraduateDescriptionFragment : Fragment() {
    private lateinit var binding:FragmentGraduateDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGraduateDescriptionBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}