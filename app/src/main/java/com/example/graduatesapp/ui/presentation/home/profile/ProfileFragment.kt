package com.example.graduatesapp.ui.presentation.home.profile

import android.content.Intent
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
import com.example.graduatesapp.databinding.FragmentProfileBinding
import com.example.graduatesapp.helper.EndPoints
import com.example.graduatesapp.ui.presentation.auth.AuthActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
   private lateinit var binding: FragmentProfileBinding
   private val profileViewModel:ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fullName.text = profileViewModel.name
        binding.email.text = profileViewModel.email
        binding.logout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Déconnexion")
                .setMessage("Voulez-vous vraiment vous déconnecter?")
                .setPositiveButton(R.string.yes) {dialog,_->
                    dialog.dismiss()
                    profileViewModel.logout()

                }
                .setNegativeButton(R.string.no) {dialog,_->
                    dialog.dismiss()
                }
                .show()
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        profileViewModel.avatar?.apply {
            val url = "${EndPoints.BASE_URL_AVATAR}/$this"
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

        profileViewModel.isCleared.observe(viewLifecycleOwner, Observer {
            val intent = Intent(requireContext(), AuthActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        })

    }
}