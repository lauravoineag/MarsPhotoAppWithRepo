package com.example.marsphotos

import com.example.marsphotos.data.NetworkMarsPhotosRepository
import com.example.marsphotos.fake.FakeDataSource
import com.example.marsphotos.fake.FakeMarsApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

//REPOSITORY test
/*To test the repository, you will need an instance of the NetworkMarsPhotosRepository.
Recall that this class depends on the MarsApiService interface.
// This is where you leverage the fake API service.*/
/*By passing the fake API service, any calls to the marsApiService property in the repository result in a call to the FakeMarsApiService.
By passing fake classes for dependencies, you can control exactly what the dependency returns. This approach ensures that the code you are testing doesn't depend on untested code or APIs that could change or have unforeseen problems. Such situations can cause your test to fail, even when nothing is wrong with the code you wrote. Fakes help create a more consistent test environment, reduce test flakiness, and facilitate concise tests that test a single functionality.*/
// When you called this function from the MarsViewModel, you called this method from a coroutine by calling it from a lambda passed to viewModelScope.launch(). You must also call suspend functions, like getMarsPhotos(), from a coroutine in a test. However, the approach is different.
class NetworkMarsRepositoryTest {

    @Test
    fun networkMarsPhotosRepository_getMarsPhotos_verifyPhotoList() =
        runTest {
            val repository = NetworkMarsPhotosRepository(
                marsApiService = FakeMarsApiService()
            )
            Assert.assertEquals(FakeDataSource.photosList, repository.getMarsPhotos())
        }
}

//The coroutine test library provides the runTest() function.
// The function takes the method that you passed in the lambda and runs it from TestScope, which inherits from CoroutineScope.

// When you call the repository in the ViewModel you created, you call the getMarsPhotos() using the viewModelScope, which is ultimately a CoroutineScope.

//Test coroutines
//In this section, you modify the networkMarsPhotosRepository_getMarsPhotos_verifyPhotoList() test
// so that the body of the test method is run from a coroutine.
