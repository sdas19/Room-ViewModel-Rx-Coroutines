package com.example.githubsampleapplication.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.work.*
import com.example.githubsampleapplication.DBCleanupWorker
import com.example.githubsampleapplication.Factory.ViewModelFactory
import com.example.githubsampleapplication.JoinTest.Repo
import com.example.githubsampleapplication.JoinTest.User
import com.example.githubsampleapplication.JoinTest.UserRepoJoin
import com.example.githubsampleapplication.R
import com.example.githubsampleapplication.RepoAdapter
import com.example.githubsampleapplication.RepoDb
import com.example.githubsampleapplication.databinding.ActivityMainBinding
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.android.support.DaggerAppCompatActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var repoDb: RepoDb

    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var errorView: ConstraintLayout
    private lateinit var retryButton: Button
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.repoRecyclerview.setLayoutManager(LinearLayoutManager(this));
        binding.repoRecyclerview.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        errorView = findViewById(R.id.no_internet_view)
        retryButton = findViewById(R.id.retry_button)

        mainActivityViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

        mainActivityViewModel.getRepoFromDb().observe(this, Observer {

            Log.d(TAG, it.toString())
            if (!it.isNullOrEmpty()) {
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.visibility = View.GONE;
                swipeRefreshLayout.isRefreshing = false
                val adapter = RepoAdapter(this@MainActivity, it)
                adapter.setHasStableIds(true)
                binding.repoRecyclerview.adapter = adapter
            } else
                mainActivityViewModel.makeRepoApiCall()


        })

        mainActivityViewModel.errorOccured.observe(this, Observer {
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.visibility = View.GONE;
            swipeRefreshLayout.isRefreshing = false

            if (it) {
                errorView.visibility = View.VISIBLE
            } else {
                errorView.visibility = View.GONE
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            mainActivityViewModel.makeRepoApiCall()
            swipeRefreshLayout.isRefreshing = false

        }

        retryButton.setOnClickListener {
            swipeRefreshLayout.isRefreshing = true
            mainActivityViewModel.makeRepoApiCall()
        }

        createDbCleanupWorkRequest()
        roomJoinTest()
    }

    public override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }

    private fun roomJoinTest() {
        val userList: MutableList<User> = ArrayList()
        userList.add(User(1, "Soumyajit"))
        userList.add(User(2, "Rimlee"))
        val repoList: MutableList<Repo> = ArrayList()
        repoList.add(Repo(1, "workmanager poc", "https://workmanager.com"))
        repoList.add(Repo(2, "livedata poc", "https://livedata.com"))
        repoList.add(Repo(3, "dagger poc", "https://dagger.com"))
        repoList.add(Repo(4, "room poc", "https://room.com"))
        repoList.add(Repo(5, "multibinding poc", "https://multibinding.com"))
        val userRepoJoinList: MutableList<UserRepoJoin> = ArrayList()
        userRepoJoinList.add(UserRepoJoin(1, 4))
        userRepoJoinList.add(UserRepoJoin(1, 2))
        userRepoJoinList.add(UserRepoJoin(2, 5))
        userRepoJoinList.add(UserRepoJoin(2, 4))
        userRepoJoinList.add(UserRepoJoin(2, 1))
        userRepoJoinList.add(UserRepoJoin(2, 3))

        repoDb.getUserDao().insertUsers(userList)
        repoDb.getRepoDao().insertRepos(repoList)
        repoDb.getUserRepoJoinDao().insert(userRepoJoinList)

        repoDb.getUserRepoJoinDao().getUsersForRepository(1).observe(this, Observer {
            Log.i(TAG, "getUsersForRepository1 $it")
        })

        repoDb.getUserRepoJoinDao().getUsersForRepository(4).observe(this, Observer {
            Log.i(TAG, "getUsersForRepository4 $it")
        })

        repoDb.getUserRepoJoinDao().getRepositoriesForUsers(2).observe(this, Observer {
            Log.i(TAG, "getRepositoriesForUser2 $it")
        })

        repoDb.getUserRepoJoinDao().getRepositoriesForUsers(1).observe(this, Observer {
            Log.i(TAG, "getRepositoriesForUser1 $it")
        })

    }

    private fun createDbCleanupWorkRequest() {

        val periodicDbCleanUpRequest = PeriodicWorkRequest.Builder(
            DBCleanupWorker::class.java, // Your worker class
            2, // repeating interval
            TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(this@MainActivity).enqueueUniquePeriodicWork(
            DBCleanupWorker::class.java.simpleName,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicDbCleanUpRequest
        )
    }
}
