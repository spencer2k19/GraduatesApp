package com.example.graduatesapp.ui.presentation.home.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduatesapp.helper.KEY_PREFERENCE
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(@ApplicationContext context:Context):ViewModel() {

    private val sharedPreferences = context.getSharedPreferences(KEY_PREFERENCE,Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private val accessToken = sharedPreferences.getString("access_token","")!!
     val name = sharedPreferences.getString("name","")!!
     val email = sharedPreferences.getString("email","")!!

    private val _isCleared = MutableLiveData<Boolean>()
    val isCleared:LiveData<Boolean> = _isCleared


    fun logout() {
        viewModelScope.launch {
            editor.clear()
            editor.commit()
            _isCleared.value = true
        }
    }




}