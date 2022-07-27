package com.example.graduatesapp.ui.presentation.auth

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduatesapp.data.models.NetworkLoginResult
import com.example.graduatesapp.data.models.ResponseLoginResult
import com.example.graduatesapp.data.models.ResponseRegisterResult
import com.example.graduatesapp.data.repositories.AuthRepository
import com.example.graduatesapp.helper.KEY_PREFERENCE
import com.example.graduatesapp.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(@ApplicationContext context:Context,
                                         private val authRepository: AuthRepository):ViewModel() {

    private val _resultRegister = MutableLiveData<Resource<ResponseRegisterResult>>()
    val resultRegister:LiveData<Resource<ResponseRegisterResult>> = _resultRegister


    fun register(name:String,email:String,password:String,confirm:String) {
        viewModelScope.launch {
            try {
                _resultRegister.value = Resource.loading(null)
                _resultRegister.value = authRepository.register(name, email, password, confirm)
            }catch (exception:Exception) {
                _resultRegister.value = Resource.error(exception.toString())
            }
        }
    }


}