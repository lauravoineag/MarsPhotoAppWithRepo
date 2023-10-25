package com.example.marsphotos

import com.example.marsphotos.fake.FakeDataSource
import com.example.marsphotos.fake.FakeNetworkMarsPhotosRepository
import com.example.marsphotos.ui.screens.MarsUiState
import com.example.marsphotos.ui.screens.MarsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

//create an instance of the MarsViewModel and pass it an instance of the fake repository you created.
//Assert that the marsUiState of your ViewModel instance matches the result of a successful call to MarsPhotosRepository.getMarsPhotos().
//You do not need to directly call MarsViewlModel.getMarsPhotos() to trigger a call to MarsPhotosRepository.getMarsPhotos().
// MarsViewModel.getMarsPhotos() is called when the ViewModel is initialized.
class MarsViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun marsViewModel_getMarsPhotos_verifyMarsUiStateSuccess() =
        runTest {
            val marsViewModel = MarsViewModel(
                marsPhotosRepository = FakeNetworkMarsPhotosRepository()
            )
            Assert.assertEquals(
                MarsUiState.Success(
                    "Success: ${FakeDataSource.photosList.size} Mars " +
                            "photos retrieved"
                ),
                marsViewModel.marsUiState
            )
        }
}