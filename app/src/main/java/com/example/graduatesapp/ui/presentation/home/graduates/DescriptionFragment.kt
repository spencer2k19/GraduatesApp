package com.example.graduatesapp.ui.presentation.home.graduates

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.graduatesapp.R
import com.example.graduatesapp.databinding.FragmentGraduateDescriptionBinding
import com.example.graduatesapp.helper.EndPoints
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DescriptionFragment : Fragment() {
    private lateinit var binding: FragmentGraduateDescriptionBinding

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
        val graduate = DescriptionFragmentArgs.fromBundle(requireArguments()).graduate
        binding.username.text = "${graduate.lastName} ${graduate.firstName}"
        binding.email.text = graduate.email
        binding.bio.text = graduate.bio
        binding.phone.text = graduate.tel
        binding.location.text = graduate.address
        graduate.avatar?.apply {
            val url = "${EndPoints.BASE_URL_FILES}/$this"
            Log.e("avatar","Url avatar: $url")
            url.let {
                Glide.with(requireContext())
                    .load(Uri.parse(it))
                    .apply(
                        RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.ic_broken_image))
                    .into(binding.avatar)
            }
        }



    }
}