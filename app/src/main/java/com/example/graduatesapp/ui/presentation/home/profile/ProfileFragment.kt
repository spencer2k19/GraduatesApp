package com.example.graduatesapp.ui.presentation.home.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.graduatesapp.R
import com.example.graduatesapp.data.models.NetworkLoginResult
import com.example.graduatesapp.data.models.NetworkUser
import com.example.graduatesapp.data.models.toListModel
import com.example.graduatesapp.databinding.FragmentProfileBinding
import com.example.graduatesapp.helper.EndPoints
import com.example.graduatesapp.helper.GALLERIE
import com.example.graduatesapp.helper.PERMISSION_REQUEST_CODE
import com.example.graduatesapp.helper.Resource
import com.example.graduatesapp.ui.presentation.auth.AuthActivity
import com.example.graduatesapp.ui.utils.FileManager
import com.example.graduatesapp.ui.utils.showMessage
import com.example.graduatesapp.ui.utils.snackbar
import com.example.graduatesapp.ui.utils.visible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        binding.refresh.setOnRefreshListener {
            profileViewModel.fetchUserInfos()
        }

        profileViewModel.avatar?.apply {
          loadImage(this)
        }

        binding.avatar.setOnClickListener {
            permissionsRequest()
        }

        listenClearedCache()
        listenUserRefreshed()
        listenImageUploaded()

    }



    private fun listenImageUploaded() {
        profileViewModel.basicResponse.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.SUCCESS -> {
                  profileViewModel.fetchUserInfos()
                }
                Resource.Status.ERROR -> {
                    binding.refresh.isRefreshing = false
                    binding.root.snackbar(it.message!!)
                }

            }
        })
    }

    private fun listenUserRefreshed() {
        profileViewModel.resultUser.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.SUCCESS -> {
                    binding.refresh.isRefreshing = false
                    loadImage(it.data!!.data.avatar)
                    profileViewModel.saveInfos(it.data.data)

                }
                Resource.Status.ERROR -> {
                    binding.refresh.isRefreshing = false
                    binding.root.snackbar(it.message!!)
                }

            }
        })
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

    private fun listenClearedCache() {
        profileViewModel.isCleared.observe(viewLifecycleOwner, Observer {
            val intent = Intent(requireContext(), AuthActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun permissionsRequest()
    {
        if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)==
            PackageManager.PERMISSION_DENIED  || ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
        {
            val permissions= arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        }
        else
        {
           pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery( )
    {
        //Intent to pick image
        val intent= Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type="image/*"
        startActivityForResult(intent, GALLERIE)
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode)
        {
            PERMISSION_REQUEST_CODE ->{
                if (grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED )
                {
                    //permission for popup granted
                    pickImageFromGallery()
                }
                else
                {
                    requireContext().showMessage(getString(R.string.permission_denied))
                }
            }

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== GALLERIE && resultCode== RESULT_OK)
        {
           val  imageUrl = FileManager.getFilePathForN(data?.data, requireContext())
            profileViewModel.uploadImage(imageUrl)
        }

    }




}