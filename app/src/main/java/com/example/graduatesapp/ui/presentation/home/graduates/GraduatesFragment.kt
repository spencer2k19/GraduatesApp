package com.example.graduatesapp.ui.presentation.home.graduates

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.graduatesapp.R
import com.example.graduatesapp.data.models.toListModel
import com.example.graduatesapp.databinding.FragmentGraduatesBinding
import com.example.graduatesapp.helper.Resource
import com.example.graduatesapp.ui.adapters.GraduateAdapter
import com.example.graduatesapp.ui.utils.snackbar
import com.example.graduatesapp.ui.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception


@AndroidEntryPoint
class GraduatesFragment : Fragment() {
    private lateinit var binding: FragmentGraduatesBinding
    private val graduateViewModel: GraduateViewModel by viewModels()
    private lateinit var graduateAdapter: GraduateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGraduatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectorId = GraduatesFragmentArgs.fromBundle(requireArguments()).sectorId


        graduateAdapter = GraduateAdapter {
            try {
                val action = GraduatesFragmentDirections.actionGraduatesFragmentToDescription(it)
                findNavController().navigate(action)
            }catch (exception:Exception) {

            }

        }




        binding.graduates.adapter = graduateAdapter
        binding.refresh.setOnRefreshListener {
            binding.diplomas.clearCheck()
            graduateViewModel.fetchGraduates(sectorId = sectorId.toString())
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        graduateViewModel.fetchGraduates(sectorId = sectorId.toString())
        listenGraduatesFetched()

        binding.diplomas.setOnCheckedStateChangeListener { group, checkedIds ->
            if(checkedIds.isNotEmpty()) {
                when(checkedIds[0]) {
                    R.id.license -> {
                        Log.e("chip","sector Id: $sectorId,  DIploma Id: 1")
                        graduateViewModel.fetchGraduates(sectorId.toString(), "1")
                    }
                    R.id.master -> {
                        Log.e("chip","sector Id: $sectorId,  DIploma Id: 2")
                        graduateViewModel.fetchGraduates(sectorId.toString(), "2")
                    }
                    R.id.doctorat -> {
                        Log.e("chip","sector Id: $sectorId,  DIploma Id: 3")
                        graduateViewModel.fetchGraduates(sectorId.toString(), "3")
                    }
                    else -> graduateViewModel.fetchGraduates()
                }
            } else {
                graduateViewModel.fetchGraduates(sectorId.toString())
            }

        }



    }

    private fun listenGraduatesFetched() {
        graduateViewModel.resultGraduates.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.emptyGraduates.visible(false)
                    binding.refresh.isRefreshing = false
                    if(graduateAdapter.currentList.isEmpty()) {
                        binding.loading.visible(true)
                    }
                }
                Resource.Status.SUCCESS -> {
                    binding.loading.visible(false)
                    val graduates = it.data!!.data.toListModel()
                    graduateAdapter.submitList(graduates)
                    if(graduates.isEmpty()) {
                        binding.emptyGraduates.visible(true)
                    }

                }
                Resource.Status.ERROR -> {
                    binding.loading.visible(false)
                    binding.root.snackbar(it.message!!)
                }

            }
        })
    }


}