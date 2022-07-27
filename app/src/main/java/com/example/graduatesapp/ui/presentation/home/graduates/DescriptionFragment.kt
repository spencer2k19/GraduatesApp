package com.example.graduatesapp.ui.presentation.home.graduates

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.graduatesapp.R
import com.example.graduatesapp.databinding.FragmentGraduateDescriptionBinding
import com.example.graduatesapp.helper.EndPoints
import com.example.graduatesapp.helper.REQUEST_PHONE_CALL
import com.example.graduatesapp.ui.models.Graduate
import com.example.graduatesapp.ui.utils.callPhone
import com.example.graduatesapp.ui.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DescriptionFragment : Fragment() {
    private lateinit var binding: FragmentGraduateDescriptionBinding
    private lateinit var graduate: Graduate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGraduateDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun permissionRequest() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CALL_PHONE
            ) ==
            PackageManager.PERMISSION_DENIED
        ) {
            val permissions = arrayOf(android.Manifest.permission.CALL_PHONE)
            requestPermissions(permissions, REQUEST_PHONE_CALL)
        } else {

            requireContext().callPhone(graduate.tel)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {

            REQUEST_PHONE_CALL -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requireContext().callPhone(graduate.tel)
                } else {
                    binding.root.snackbar("Vous devez activer la demande d'autorisation pour appeler ")
                }
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        graduate = DescriptionFragmentArgs.fromBundle(requireArguments()).graduate
        binding.username.text = "${graduate.lastName} ${graduate.firstName}"
        binding.email.text = graduate.email
        binding.bio.text = graduate.bio
        binding.phone.text = graduate.tel
        binding.location.text = graduate.address


        graduate.banner?.apply {
            val url = "${EndPoints.BASE_URL_FILES}/$this"
            Log.e("avatar", "Url avatar: $url")
            url.let {
                Glide.with(requireContext())
                    .load(Uri.parse(it))
                    .apply(
                        RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(binding.banner)
            }
        }


        graduate.avatar?.apply {
            val url = "${EndPoints.BASE_URL_FILES}/$this"
            Log.e("avatar", "Url avatar: $url")
            url.let {
                Glide.with(requireContext())
                    .load(Uri.parse(it))
                    .apply(
                        RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(binding.avatar)
            }
        }

        binding.phone.setOnClickListener { permissionRequest() }
        binding.email.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${graduate.email}"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello ${graduate.firstName}")
            startActivity(Intent.createChooser(emailIntent, "Ouvrir avec"))
        }
        if (!graduate.linkedln.isNullOrEmpty()) {
            binding.link.text = graduate.linkedln
            binding.link.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(graduate.linkedln)
                startActivity(intent)
            }
        } else {
            binding.link.isClickable = false
            binding.link.text = getString(R.string.non_defini)
        }


    }
}