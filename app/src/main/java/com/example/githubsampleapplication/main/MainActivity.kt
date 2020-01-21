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
import com.example.githubsampleapplication.R
import com.example.githubsampleapplication.RepoAdapter
import com.example.githubsampleapplication.RepoDao
import com.example.githubsampleapplication.databinding.ActivityMainBinding
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.android.support.DaggerAppCompatActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService


class MainActivity : DaggerAppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var errorView: ConstraintLayout
    private lateinit var retryButton: Button
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this,"MainActivity",Toast.LENGTH_LONG).show()
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
            } else {
                swipeRefreshLayout.isRefreshing = true
                mainActivityViewModel.makeRepoApiCall()
            }

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


    }

    public override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
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
