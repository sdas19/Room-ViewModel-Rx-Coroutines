package com.example.githubsampleapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.runner.RunWith
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import com.example.githubsampleapplication.model.BuiltByResponseModel
import com.example.githubsampleapplication.model.RepositoryResponseModel
import com.example.githubsampleapplication.testUtil.LiveDataTestUtil
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class RepositoryDbUnitTest {

    private lateinit var repositoryDB: RepoDb

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        repositoryDB = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            RepoDb::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        repositoryDB.close()
    }


    @Test
    fun testInsertionAndFetchValidation() {

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

        repositoryDB.getRepositoryDao().insertAll(repositoryList)
        val repositoryListFromDB = repositoryDB.getRepositoryDao().getAllRepos()

        assertNotNull(repositoryListFromDB)
        assertEquals(1, LiveDataTestUtil.getValue(repositoryDB.getRepositoryDao().getAllRepos()).size)
        assertEquals(
            "graceavery",
            LiveDataTestUtil.getValue(repositoryDB.getRepositoryDao().getAllRepos())[0].author
        )
    }

    @Test
    fun testDeletion() {

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
        repositoryDB.getRepositoryDao().deleteAll()

        assertEquals(0, LiveDataTestUtil.getValue(repositoryDB.getRepositoryDao().getAllRepos()).size)

    }

}

