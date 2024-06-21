package com.dicoding.storyapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.storyapp.data.StoryRepository
import com.dicoding.storyapp.data.response.Story
import com.dicoding.storyapp.data.Result

class DetailStoryViewModel (private val storyRepository: StoryRepository) : ViewModel() {

    fun getDetailStory(id: String): LiveData<Result<Story>> = liveData {
        emit(Result.Loading)
        try {
            val response = storyRepository.getDetailStory(id)
            if (response.error == true) {
                emit(Result.Error(response.message ?: "Unknown error"))
            } else {
                response.story?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Story not found"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Network error"))
        }
    }
}