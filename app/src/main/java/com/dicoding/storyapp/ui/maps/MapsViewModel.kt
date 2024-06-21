package com.dicoding.storyapp.ui.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.data.StoryRepository
import com.dicoding.storyapp.data.response.StoryResponse
import kotlinx.coroutines.launch

class MapsViewModel (private var storyRepository: StoryRepository): ViewModel(){

    private val _getLocation = MutableLiveData<StoryResponse>()
    val getLocation: LiveData<StoryResponse> = _getLocation

    fun getLocation(location: Int) {
        viewModelScope.launch {
            try {
                val response = storyRepository.getStoriesWithLocation(location)
                Log.d("API Response", response.toString())
                _getLocation.postValue(response)
            } catch (e: Exception) {
                Log.e("API Error", e.message.toString())
                _getLocation.postValue(StoryResponse(error = true, message = e.message ?: "Unknown error"))
            }
        }
    }
}