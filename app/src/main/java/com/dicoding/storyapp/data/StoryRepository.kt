package com.dicoding.storyapp.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.storyapp.data.pagging.StoryPagingSource
import com.dicoding.storyapp.data.response.ListStoryItem
import com.dicoding.storyapp.data.response.NewStoryResponse
import com.dicoding.storyapp.data.response.StoryDetailResponse
import com.dicoding.storyapp.data.response.StoryResponse
import com.dicoding.storyapp.data.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository (private val apiService: ApiService) {

    suspend fun getStories(): StoryResponse {
        return apiService.getStories()
    }

    suspend fun getDetailStory(id: String): StoryDetailResponse {
        return apiService.getDetailStories(id)
    }

    suspend fun uploadImage(file: MultipartBody.Part, description: RequestBody): NewStoryResponse {
        return apiService.uploadImage(file, description)
    }

    suspend fun getStoriesWithLocation(location: Int = 1): StoryResponse {
        return apiService.getStoriesWithLocation(location)
    }

    fun getStoryList(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

    companion object {
        fun getInstance(apiService: ApiService) = StoryRepository(apiService)
    }
}