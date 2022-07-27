package com.example.graduatesapp.ui.presentation.home.graduates


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduatesapp.data.models.ResponseGraduates
import com.example.graduatesapp.data.repositories.GraduatesRepository
import com.example.graduatesapp.helper.KEY_PREFERENCE
import com.example.graduatesapp.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraduateViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val graduatesRepository: GraduatesRepository
) : ViewModel() {

    private val _resultGraduates = MutableLiveData<Resource<ResponseGraduates>>()
    val resultGraduates: LiveData<Resource<ResponseGraduates>> = _resultGraduates

    private val sharedPreferences = context.getSharedPreferences(KEY_PREFERENCE,Context.MODE_PRIVATE)
    private val accessToken = sharedPreferences.getString("access_token","")!!


    fun fetchGraduates(sectorId:String = "",diplomaId:String = "") {
        viewModelScope.launch {
            try {
                _resultGraduates.value = Resource.loading(null)
                _resultGraduates.value = graduatesRepository.fetchGraduates(accessToken,sectorId, diplomaId)
            }catch (exception: Exception) {
                _resultGraduates.value = Resource.error(exception.toString())
            }
        }
    }


}