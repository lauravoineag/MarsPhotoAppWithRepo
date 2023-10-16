package com.example.marsphotos.fake

import com.example.marsphotos.model.MarsPhoto

/*the repository depends on the API service.
// To create a repository test, there must be a fake API service that returns the fake data you just created.
When this fake API service is passed into the repository, the repository receives the fake data when the methods in the fake API service are called.*/
object FakeDataSource {

    const val idOne = "img1"
    const val idTwo = "img2"
    const val imgOne = "url.1"
    const val imgTwo = "url.2"
    val photosList = listOf(
        MarsPhoto(id = idOne, imgSrc = imgOne),
        MarsPhoto(id = idTwo, imgSrc = imgTwo)
    )
}