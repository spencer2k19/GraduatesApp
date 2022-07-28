package com.example.graduatesapp.ui.presentation.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduatesapp.data.models.ResponseLoginResult
import com.example.graduatesapp.data.models.ResponseSectors
import com.example.graduatesapp.data.repositories.AuthRepository
import com.example.graduatesapp.data.repositories.GraduatesRepository
import com.example.graduatesapp.helper.KEY_PREFERENCE
import com.example.graduatesapp.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val graduatesRepository: GraduatesRepository
) : ViewModel() {

    private val _resultSectors = MutableLiveData<Resource<ResponseSectors>>()
    val resultSectors: LiveData<Resource<ResponseSectors>> = _resultSectors

    private val sharedPreferences = context.getSharedPreferences(KEY_PREFERENCE,Context.MODE_PRIVATE)
    private val accessToken = sharedPreferences.getString("access_token","")!!
    val avatar = sharedPreferences.getString("avatar","")


    fun fetchSectors() {
        viewModelScope.launch {
            try {
                _resultSectors.value = Resource.loading(null)
                _resultSectors.value = graduatesRepository.fetchSectors(accessToken)
            }catch (exception: Exception) {
                _resultSectors.value = Resource.error(exception.toString())
            }
        }
    }


}