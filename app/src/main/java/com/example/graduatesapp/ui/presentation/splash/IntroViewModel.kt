package com.example.graduatesapp.ui.presentation.splash

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.graduatesapp.helper.KEY_PREFERENCE
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(@ApplicationContext context:Context):ViewModel() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(KEY_PREFERENCE,Context.MODE_PRIVATE)
    private val _isLogged = MutableLiveData<Boolean>()
    val isLogged: LiveData<Boolean> = _isLogged

    fun startup() {
        _isLogged.value = !sharedPreferences.getString("access_token","").isNullOrEmpty()
    }

}