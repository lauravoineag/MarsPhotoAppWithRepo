package com.example.marsphotos.data

import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.network.MarsApiService

/*
 /**
 * Repository that fetch mars photos list from marsApi.
 */

1.In the interface, add an abstract function called getMarsPhotos(),
 which returns a list of MarsPhoto objects.
 It is called from a coroutine, so declare it with suspend.
 Remember when you called this function from the MarsViewModel,
 you called this method from a coroutine by calling it from a lambda passed to viewModelScope.launch().
 You must also call suspend functions, like getMarsPhotos(), from a coroutine in a test. */

interface MarsPhotosRepository {
    /** Fetches list of MarsPhoto from marsApi */
    suspend fun getMarsPhotos(): List<MarsPhoto>
}

// 2. /**
// * Network Implementation of Repository that fetch mars photos list from marsApi.
// */
// returns data from calling MarsApi.retrofitService.getPhotos()
class NetworkMarsPhotosRepository(private val marsApiService: MarsApiService) :MarsPhotosRepository {
    /** Fetches list of MarsPhoto from marsApi*/
    override suspend fun getMarsPhotos(): List<MarsPhoto> = marsApiService.getPhotos()
}