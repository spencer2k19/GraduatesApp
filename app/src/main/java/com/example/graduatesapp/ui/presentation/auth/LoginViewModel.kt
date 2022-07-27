package com.example.graduatesapp.ui.presentation.auth

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduatesapp.data.models.NetworkLoginResult
import com.example.graduatesapp.data.models.ResponseLoginResult
import com.example.graduatesapp.data.repositories.AuthRepository
import com.example.graduatesapp.helper.KEY_PREFERENCE
import com.example.graduatesapp.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(@ApplicationContext context:Context,
private val authRepository: AuthRepository):ViewModel() {

    private val _resultLogin = MutableLiveData<Resource<ResponseLoginResult>>()
    val resultLogin:LiveData<Resource<ResponseLoginResult>> = _resultLogin

    private val sharedPreferences = context.getSharedPreferences(KEY_PREFERENCE,Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveInfos(info:NetworkLoginResult) {
        viewModelScope.launch {
            info.apply {
                editor.putString("access_token","${info.tokenType} ${info.token}")
                editor.putString("token_type",info.tokenType)
                editor.putString("name",info.user.name)
                editor.putString("email",info.user.email)
                editor.putString("avatar",info.user.avatar)
                editor.putInt("id",info.user.id)
                editor.commit()
            }

        }
    }

    fun login(email:String,password:String) {
        viewModelScope.launch {
            try {
                _resultLogin.value = Resource.loading(null)
                _resultLogin.value = authRepository.login(email,password)
            }catch (exception:Exception) {
                _resultLogin.value = Resource.error(exception.toString())
            }
        }
    }


}