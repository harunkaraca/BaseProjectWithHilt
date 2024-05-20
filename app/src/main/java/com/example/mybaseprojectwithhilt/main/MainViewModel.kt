package com.example.mybaseprojectwithhilt.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybaseprojectwithhilt.data.model.Country
import com.example.mybaseprojectwithhilt.data.source.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.mybaseprojectwithhilt.data.source.Result

@HiltViewModel
class MainViewModel@Inject constructor(private val repository: DefaultRepository):ViewModel() {
    private val _items = MutableLiveData<List<Country>>().apply { value = emptyList() }
    val items: LiveData<List<Country>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _isDataLoadingError = MutableLiveData<Boolean>()
    val isDataLoadingError = _isDataLoadingError

    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText

    init {
       loadCountry()
    }
    fun refresh(){
//        loadCountry()
    }
    fun loadCountry(){
        Log.i("MainViewModel","run loadCountry")
        viewModelScope.launch {
            val result=repository.getCountryList()
            if(result is Result.Success){
                _isDataLoadingError.value = false
                _items.value = result.data
            }else if(result is Result.Error){
                _isDataLoadingError.value = true
                _snackbarText.value=result.exception.message
            }else{
                _isDataLoadingError.value = false
                _items.value = emptyList()
            }
            _dataLoading.value = false
        }
    }
}