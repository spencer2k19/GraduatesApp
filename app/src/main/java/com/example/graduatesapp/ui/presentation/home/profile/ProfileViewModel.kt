package com.example.graduatesapp.ui.presentation.home.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduatesapp.data.models.NetworkBasicResponse
import com.example.graduatesapp.data.models.NetworkUser
import com.example.graduatesapp.data.models.ResponseUser
import com.example.graduatesapp.data.repositories.UserRepository
import com.example.graduatesapp.helper.KEY_PREFERENCE
import com.example.graduatesapp.helper.Resource
import com.example.graduatesapp.ui.utils.FileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(@ApplicationContext context:Context,
private val userRepository: UserRepository):ViewModel() {

    private val sharedPreferences = context.getSharedPreferences(KEY_PREFERENCE,Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private val accessToken = sharedPreferences.getString("access_token","")!!

    private val _resultUser = MutableLiveData<Resource<ResponseUser>>()
    val resultUser:LiveData<Resource<ResponseUser>> = _resultUser

    val basicResponse = MutableLiveData<Resource<NetworkBasicResponse>>()


     val name = sharedPreferences.getString("name","")!!
     val email = sharedPreferences.getString("email","")!!
     val avatar = sharedPreferences.getString("avatar","")

    private val _isCleared = MutableLiveData<Boolean>()
    val isCleared:LiveData<Boolean> = _isCleared


    fun saveInfos(info: NetworkUser) {
        viewModelScope.launch {
            info.apply {
                editor.putString("name",this.name)
                editor.putString("email",this.email)
                editor.putString("avatar",this.avatar)
                editor.putInt("id",this.id)
                editor.commit()
            }

        }
    }

    fun uploadImage(imageUrl:String) {
       viewModelScope.launch {
           try {
               basicResponse.value = Resource.loading(null)
               basicResponse.value = userRepository.uploadImage(accessToken,imageUrl)
           }catch (exception: Exception) {
               _resultUser.value = Resource.error(exception.toString())
           }
       }
    }

    fun fetchUserInfos() {
        viewModelScope.launch {
            try {
                _resultUser.value = Resource.loading(null)
                _resultUser.value = userRepository.fetchUserInfos(accessToken)
            }catch (exception: Exception) {
                _resultUser.value = Resource.error(exception.toString())
            }
        }
    }


    fun logout() {
        viewModelScope.launch {
            editor.clear()
            editor.commit()
            _isCleared.value = true
        }
    }




}