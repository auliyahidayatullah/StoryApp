package com.dicoding.storyapp.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.storyapp.data.StoryRepository
import com.dicoding.storyapp.data.UserRepository
import com.dicoding.storyapp.data.preferences.UserModel
import com.dicoding.storyapp.data.response.ListStoryItem
import kotlinx.coroutines.launch
import com.dicoding.storyapp.data.Result
import com.dicoding.storyapp.data.response.StoryResponse
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val userRepository: UserRepository, private val storyRepository: StoryRepository) : ViewModel() {

    val storyList: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStoryList().cachedIn(viewModelScope)

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun getStories(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = storyRepository.getStories()
            if (response.error == true) {
                emit(Result.Error(response.message ?: "Unknown error"))
            } else {
                emit(Result.Success(response.listStory!!.filterNotNull()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Network error"))
        }
    }

}