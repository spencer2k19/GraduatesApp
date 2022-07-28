package com.example.graduatesapp.ui.presentation.home

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.graduatesapp.R
import com.example.graduatesapp.data.models.toListModel
import com.example.graduatesapp.databinding.FragmentHomeBinding
import com.example.graduatesapp.helper.EndPoints
import com.example.graduatesapp.helper.Resource
import com.example.graduatesapp.ui.adapters.SectorAdapter
import com.example.graduatesapp.ui.utils.snackbar
import com.example.graduatesapp.ui.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private val homeViewModel:HomeViewModel by viewModels()
    private lateinit var sectorAdapter: SectorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectorAdapter = SectorAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToDescriptionSectorFragment(it)
            findNavController().navigate(action)
        }
        binding.listSectors.adapter = sectorAdapter
        binding.swipeRefresh.setOnRefreshListener {
            homeViewModel.fetchSectors()
        }
        homeViewModel.fetchSectors()
        listenSectorsFetched()

        binding.voirPlus.setOnClickListener {
            try {
                findNavController().navigate(R.id.action_homeFragment_to_sectorsFragment)
            }catch(exception:Exception) {

            }

        }
        binding.avatar.setOnClickListener {
            try {
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
            }catch(exception:Exception) {

            }
        }

        loadImage(homeViewModel.avatar)

    }

    private fun loadImage(link:String?) {

        val url = "${EndPoints.BASE_URL_AVATAR}/$link"
        Log.e("avatar", "Url avatar: $url")
        url.let {
            Glide.with(requireContext())
                .load(Uri.parse(it))
                .apply(
                    RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.blank)
                )
                .into(binding.avatar)
        }
    }

    private fun listenSectorsFetched() {
        homeViewModel.resultSectors.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Resource.Status.LOADING -> {
                    binding.swipeRefresh.isRefreshing = false
                    if(sectorAdapter.currentList.isEmpty()) {
                        binding.loading.visible(true)
                    }

                }
                Resource.Status.SUCCESS -> {
                    binding.loading.visible(false)
                    val sectors = it.data!!.data.toListModel()
                    sectorAdapter.submitList(sectors.subList(0,4))
                }
                Resource.Status.ERROR -> {
                    binding.loading.visible(false)
                    binding.root.snackbar(it.message!!)
                }

            }
        })
    }


}