package com.example.githubsampleapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.githubsampleapplication.main.MainActivityViewModel
import com.example.githubsampleapplication.model.BuiltByResponseModel
import com.example.githubsampleapplication.model.RepositoryResponseModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainActivityViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var apiClient: ApiClient
    @Mock
    lateinit var repoDao: RepoDao
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private val observer: Observer<Boolean> = mock()


    @Before
    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainActivityViewModel =
            MainActivityViewModel(apiClient, repoDao, TestCoroutineContextProvider())
        mainActivityViewModel.errorOccured.observeForever(observer)
    }

    @Test
    fun testApiFetchDataSuccess() = runBlocking {

        // Mock API proper response

        testCoroutineRule.runBlockingTest {
            val repositoryList: MutableList<RepositoryResponseModel> = ArrayList()
            val builtByList: MutableList<BuiltByResponseModel> = ArrayList()
            builtByList.add(
                BuiltByResponseModel(
                    "graceavery", "https://github.com/graceavery",
                    "https://avatars2.githubusercontent.com/u/5048800"
                )
            )
            val repository = RepositoryResponseModel(
                1, "graceavery", "tamagotchiTemp", "https://github.com/graceavery.png",
                "https://github.com/graceavery/tamagotchiTemp", "", "", "",
                595, 25, 76, builtByList
            )
            repositoryList.add(repository)
            val expectedResponse = Response.success(repositoryList as List<RepositoryResponseModel>)

            `when`(apiClient.getRepositoryResponse()).thenReturn(expectedResponse)

            val observer = mock(Observer::class.java) as Observer<Boolean>

            mainActivityViewModel.errorOccured.observeForever(observer)
            mainActivityViewModel.makeRepoApiCall()

            assertNotNull(mainActivityViewModel.errorOccured.value)
            assertEquals(false, mainActivityViewModel.errorOccured.value)
        }
    }


    @Test
    fun testApiFetchDataError() = runBlocking {

        // Mock API error response

        testCoroutineRule.runBlockingTest {
            val mockException: HttpException = mock()
            whenever(mockException.code()).thenReturn(401)
            `when`(apiClient.getRepositoryResponse()).thenThrow(mockException)

            val observer = mock(Observer::class.java) as Observer<Boolean>
            mainActivityViewModel.errorOccured.observeForever(observer)
            mainActivityViewModel.makeRepoApiCall()

            assertNotNull(mainActivityViewModel.errorOccured.value)
            assertEquals(true, mainActivityViewModel.errorOccured.value)
        }

    }

}