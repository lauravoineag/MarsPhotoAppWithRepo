package com.example.marsphotos.data

import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.network.MarsApiService

/*1.In the interface, add an abstract function called getMarsPhotos(),
 which returns a list of MarsPhoto objects.
 It is called from a coroutine, so declare it with suspend.*/

interface MarsPhotosRepository {
    suspend fun getMarsPhotos(): List<MarsPhoto>
}

// 2. returns data from calling MarsApi.retrofitService.getPhotos()
class NetworkMarsPhotosRepository(private val marsApiService: MarsApiService) :MarsPhotosRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> = marsApiService.getPhotos()
}