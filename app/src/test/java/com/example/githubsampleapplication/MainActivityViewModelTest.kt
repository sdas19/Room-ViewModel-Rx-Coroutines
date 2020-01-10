package com.example.githubsampleapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.githubsampleapplication.main.MainActivityViewModel
import com.example.githubsampleapplication.model.BuiltByResponseModel
import com.example.githubsampleapplication.model.RepositoryResponseModel
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import org.mockito.Mock
import org.mockito.Mockito.*
import java.net.SocketException
import org.junit.*


@RunWith(JUnit4::class)
class MainActivityViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Mock
    lateinit var apiClient: ApiClient
    @Mock
    lateinit var repositoryDao: RepositoryDao
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private val observer: Observer<Boolean> = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainActivityViewModel = MainActivityViewModel(apiClient, repositoryDao)
        mainActivityViewModel.errorOccured.observeForever(observer)
    }

    @Test
    fun testApiFetchDataSuccess() {

        // Mock API proper response

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

        `when`(apiClient.getRepositoryResponse()).thenAnswer {
            return@thenAnswer Single.just(repositoryList)
        }

        val observer = mock(Observer::class.java) as Observer<Boolean>

        this.mainActivityViewModel.errorOccured.observeForever(observer)
        this.mainActivityViewModel.makeRepoApiCall()

        assertNotNull(this.mainActivityViewModel.errorOccured.value)
        assertEquals(false, this.mainActivityViewModel.errorOccured.value)
    }


    @Test
    fun testApiFetchDataError() {

        // Mock API error response

        `when`(apiClient.getRepositoryResponse()).thenAnswer {
            return@thenAnswer Single.error<SocketException>(SocketException("Api error"))
        }

        val observer = mock(Observer::class.java) as Observer<Boolean>

        this.mainActivityViewModel.errorOccured.observeForever(observer)
        this.mainActivityViewModel.makeRepoApiCall()

        assertNotNull(this.mainActivityViewModel.errorOccured.value)
        assertEquals(true, this.mainActivityViewModel.errorOccured.value)
    }


}