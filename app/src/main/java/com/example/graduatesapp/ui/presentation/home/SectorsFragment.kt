package com.example.graduatesapp.ui.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.graduatesapp.R
import com.example.graduatesapp.data.models.toListModel
import com.example.graduatesapp.databinding.FragmentHomeBinding
import com.example.graduatesapp.databinding.FragmentSectorsBinding
import com.example.graduatesapp.helper.Resource
import com.example.graduatesapp.ui.adapters.SectorAdapter
import com.example.graduatesapp.ui.utils.snackbar
import com.example.graduatesapp.ui.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SectorsFragment : Fragment() {
    private lateinit var binding:FragmentSectorsBinding
    private val homeViewModel:HomeViewModel by viewModels()
    private lateinit var sectorAdapter: SectorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSectorsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectorAdapter = SectorAdapter {
            val action = SectorsFragmentDirections.actionSectorsFragmentToDescriptionSectorFragment(it)
            findNavController().navigate(action)
        }
        binding.sectors.adapter = sectorAdapter
        binding.refresh.setOnRefreshListener {
            homeViewModel.fetchSectors()
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        homeViewModel.fetchSectors()
        listenSectorsFetched()

    }

    private fun listenSectorsFetched() {
        homeViewModel.resultSectors.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Resource.Status.LOADING -> {
                    binding.refresh.isRefreshing = false
                    if(sectorAdapter.currentList.isEmpty()) {
                        binding.loading.visible(true)
                    }
                }
                Resource.Status.SUCCESS -> {
                    binding.loading.visible(false)
                    val sectors = it.data!!.data.toListModel()
                    sectorAdapter.submitList(sectors)
                }
                Resource.Status.ERROR -> {
                    binding.loading.visible(false)
                    binding.root.snackbar(it.message!!)
                }

            }
        })
    }


}