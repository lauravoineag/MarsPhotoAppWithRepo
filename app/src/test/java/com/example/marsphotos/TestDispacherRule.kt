package com.example.marsphotos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
): TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

/*
* Recall that the MarsViewModel calls the repository using viewModelScope.launch(). This instruction launches a new coroutine under the default coroutine dispatcher, which is called the Main dispatcher. The Main dispatcher wraps the Android UI thread. The reason for the preceding error is the Android UI thread is not available in a unit test. Unit tests are executed on your workstation, not an Android device or Emulator. If code under a local unit test references the Main dispatcher, an exception (like the one above) is thrown when the unit tests are run. To overcome this issue, you must explicitly define the default dispatcher when running unit tests. Head to the next section to learn how to do so.

Create a test dispatcher
Since the Main dispatcher is only available in a UI context, you must replace it with a unit-test-friendly dispatcher. The Kotlin Coroutines library provides a coroutine dispatcher for this purpose called TestDispatcher. The TestDispatcher needs to be used instead of the Main dispatcher for any unit test in which a new coroutine is made, as is the case with the getMarsPhotos() function from the view model.

To replace the Main dispatcher with a TestDispatcher in all cases, use the Dispatchers.setMain() function. You can use the Dispatchers.resetMain() function to reset the thread dispatcher back to the Main dispatcher. To avoid duplicating the code that replaces the Main dispatcher in each test, you can extract it into a JUnit test rule. A TestRule provides a way to control the environment under which a test is run. A TestRule may add additional checks, it may perform necessary setup or cleanup for tests, or it may observe test execution to report it elsewhere. They can be easily shared between test classes.
* This parameter enables the use of different dispatchers, such as StandardTestDispatcher. This constructor parameter needs to have a default value set to an instance of the UnconfinedTestDispatcher object. The UnconfinedTestDispatcher class inherits from the TestDispatcher class and it specifies that tasks must not be executed in any particular order. This pattern of execution is good for simple tests as coroutines are handled automatically. Unlike UnconfinedTestDispatcher, the StandardTestDispatcher class enables full control over coroutine execution. This way is preferable for complicated tests that require a manual approach, but it is not necessary for the tests here.
* The primary goal of this test rule is to replace the Main dispatcher with a test dispatcher before a test begins to execute. The starting() function of the TestWatcher class executes before a given test executes. Override the starting() function.
* After test execution is finished, reset the Main dispatcher by overriding the finished() method. Call the Dispatchers.resetMain() function.
* */