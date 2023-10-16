package com.example.marsphotos.fake

import com.example.marsphotos.data.NetworkMarsPhotosRepository
import junit.framework.TestCase.assertEquals
import org.junit.Test
/*To test the repository, you will need an instance of the NetworkMarsPhotosRepository.
Recall that this class depends on the MarsApiService interface.
// This is where you leverage the fake API service.*/
/*By passing the fake API service, any calls to the marsApiService property in the repository result in a call to the FakeMarsApiService.
By passing fake classes for dependencies, you can control exactly what the dependency returns. This approach ensures that the code you are testing doesn't depend on untested code or APIs that could change or have unforeseen problems. Such situations can cause your test to fail, even when nothing is wrong with the code you wrote. Fakes help create a more consistent test environment, reduce test flakiness, and facilitate concise tests that test a single functionality.*/
// When you called this function from the MarsViewModel, you called this method from a coroutine by calling it from a lambda passed to viewModelScope.launch(). You must also call suspend functions, like getMarsPhotos(), from a coroutine in a test. However, the approach is different.
@Test
fun networkMarsPhotosRepository_getMarsPhotos_verifyPhotoList(){
    val repository = NetworkMarsPhotosRepository(marsApiService = FakeMarsApiService())
    assertEquals(FakeDataSource.photosList,repository.getMarsPhotos())
}