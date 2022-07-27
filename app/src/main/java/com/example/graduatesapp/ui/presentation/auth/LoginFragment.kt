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
import com.example.graduatesapp.databinding.FragmentLoginBinding
import com.example.graduatesapp.helper.Resource
import com.example.graduatesapp.ui.presentation.home.HomeActivity
import com.example.graduatesapp.ui.utils.setShowProgress
import com.example.graduatesapp.ui.utils.snackbar
import com.example.graduatesapp.ui.utils.validateField
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding:FragmentLoginBinding
    private val loginViewModel:LoginViewModel by viewModels()
    private val errors = mutableListOf<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.setOnClickListener {
            verifyEntryUser()
        }
        binding.register.setOnClickListener {
           try {
               findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
           }catch (exception:Exception) {

           }
        }
        registerEntryResult()
    }

    private fun verifyEntryUser() {
        val email = binding.emailEdit.text.toString().trim()
        val password = binding.passwordEdit.text.toString().trim()
        errors.clear()
        errors.add(validateField(requireContext(),email,binding.emailInput))
        errors.add(validateField(requireContext(),password,binding.passwordInput))
        if(!errors.contains(false)){
            loginViewModel.login(email,password)
        }
    }

    private fun registerEntryResult() {
        loginViewModel.resultLogin.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Resource.Status.LOADING -> {
                    binding.login.setShowProgress(true,getString(R.string.valider))
                }
                Resource.Status.SUCCESS -> {
                    binding.login.setShowProgress(false,getString(R.string.valider))
                    loginViewModel.saveInfos(it.data!!.data)
                    val intent = Intent(requireContext(),HomeActivity::class.java)
                    intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                Resource.Status.ERROR -> {
                    binding.login.setShowProgress(false,getString(R.string.valider))
                    binding.root.snackbar(it.message!!)
                }

            }
        })
    }
}