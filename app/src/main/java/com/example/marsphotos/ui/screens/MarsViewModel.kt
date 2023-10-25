/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.model.MarsPhoto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface MarsUiState {
    data class Success(val photos: MarsPhoto) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

//Android framework does not allow a ViewModel to be passed values in the constructor when created, we implement a ViewModelProvider.Factory object, which lets us get around this limitation.
//The Factory pattern is a creational pattern used to create objects. The MarsViewModel.Factory object uses the application container to retrieve the marsPhotosRepository, and then passes this repository to the ViewModel when the ViewModel object is created.
class MarsViewModel(private val marsPhotosRepository: MarsPhotosRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    //Call getMarsPhotos() on init so we can display status immediately.
    init { getMarsPhotos() }

    /*** Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].*/
    //the ViewModel code to use the repository to get the data
    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            marsUiState = try {
                //Step 1.Get image URL > Step 2 MarsPhotoCard
                MarsUiState.Success(marsPhotosRepository.getMarsPhotos()[0])
            }
            catch (e: IOException) { MarsUiState.Error }
            catch (e: HttpException) { MarsUiState.Error }
        }
    }

    /**
     * Factory for [MarsViewModel] that takes [MarsPhotosRepository] as a dependency
     */

    //A companion object helps us by having a single instance of an object that is used by everyone without needing to create a new instance of an expensive object.
    // This is an implementation detail, and separating it lets us make changes without impacting other parts of the app's code.
    //The APPLICATION_KEY is part of the ViewModelProvider.
    // AndroidViewModelFactory.Companion object and is used to find the app's MarsPhotosApplication object, which has the container property used to retrieve the repository used for dependency injection.
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
            }
        }
    }
}
