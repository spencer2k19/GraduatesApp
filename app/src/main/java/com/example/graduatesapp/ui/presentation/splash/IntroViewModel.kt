package com.example.graduatesapp.ui.presentation.splash

import android.content.Context
import android.content.SharedPreferences
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
class IntroViewModel @Inject constructor(@ApplicationContext context:Context):ViewModel() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(KEY_PREFERENCE,Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private val _isLogged = MutableLiveData<Boolean>()
    val isLogged: LiveData<Boolean> = _isLogged

    private val _skipIntro = MutableLiveData<Boolean>()
    val skipIntro:LiveData<Boolean> = _skipIntro

    fun startup() {
        if(sharedPreferences.getBoolean("isFirstStart",true)) {
            _skipIntro.value = true
        } else {
            _isLogged.value = !sharedPreferences.getString("access_token","").isNullOrEmpty()
        }
    }

    fun skipIntro() {
        viewModelScope.launch {
            editor.putBoolean("isFirstStart",false)
            editor.commit()
            _skipIntro.value = true
        }

    }

}