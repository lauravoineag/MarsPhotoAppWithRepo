package com.example.marsphotos.fake

import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.network.MarsApiService

//gets the data from the source
class FakeMarsApiService:MarsApiService {
    override suspend fun getPhotos(): List<MarsPhoto> {
        return FakeDataSource.photosList
    }
}