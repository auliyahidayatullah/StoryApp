package com.dicoding.storyapp.ui.newstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.data.StoryRepository
import com.dicoding.storyapp.data.response.NewStoryResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.dicoding.storyapp.data.Result
import com.dicoding.storyapp.data.UserRepository
import com.dicoding.storyapp.data.preferences.UserModel
import kotlinx.coroutines.flow.Flow

class NewStoryViewModel (private val storyRepository: StoryRepository, private val userRepository: UserRepository) : ViewModel() {
    private val _uploadResult = MutableLiveData<Result<NewStoryResponse>>()
    val uploadResult: LiveData<Result<NewStoryResponse>> = _uploadResult

    fun uploadImage(file: MultipartBody.Part, description: RequestBody) {
        viewModelScope.launch {
            try {
                val response = storyRepository.uploadImage(file, description)
                _uploadResult.value = Result.Success(response)

            } catch (e: Exception) {
                _uploadResult.value = Result.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun getUserToken(): Flow<UserModel> {
        return userRepository.getSession()
    }
}