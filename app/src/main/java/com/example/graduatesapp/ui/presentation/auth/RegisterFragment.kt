package com.example.graduatesapp.ui.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.graduatesapp.R
import com.example.graduatesapp.databinding.FragmentRegisterBinding
import com.example.graduatesapp.helper.Resource
import com.example.graduatesapp.ui.presentation.home.HomeActivity
import com.example.graduatesapp.ui.utils.setShowProgress
import com.example.graduatesapp.ui.utils.snackbar
import com.example.graduatesapp.ui.utils.validateField
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding:FragmentRegisterBinding
    private val registerViewModel:RegisterViewModel by viewModels()
    private val errors = mutableListOf<Boolean>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.connexion.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.register.setOnClickListener {
            verifyEntryUser()
        }
       registerEntryResult()

    }

    private fun verifyEntryUser() {
        val name = binding.nameEdit.text.toString().trim()
        val email = binding.emailEdit.text.toString().trim()
        val password = binding.passwordEdit.text.toString().trim()
        val confirm = binding.cPasswordEdit.text.toString().trim()
        errors.clear()
        errors.add(validateField(requireContext(),name,binding.nameInput))
        errors.add(validateField(requireContext(),email,binding.emailInput))
        errors.add(validateField(requireContext(),password,binding.passwordInput))
        errors.add(validateField(requireContext(),confirm,binding.cPasswordInput))

        if(!errors.contains(false)){
            registerViewModel.register(name, email, password, confirm)
        }
    }

    private fun registerEntryResult() {
        registerViewModel.resultRegister.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Resource.Status.LOADING -> {
                    binding.register.setShowProgress(true,getString(R.string.valider))
                }
                Resource.Status.SUCCESS -> {
                    binding.register.setShowProgress(false,getString(R.string.valider))
                    findNavController().popBackStack()
                }
                Resource.Status.ERROR -> {
                    binding.register.setShowProgress(false,getString(R.string.valider))
                    binding.root.snackbar(it.message!!)
                }

            }
        })
    }


}