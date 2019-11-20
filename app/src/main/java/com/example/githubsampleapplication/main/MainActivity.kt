package com.example.githubsampleapplication.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsampleapplication.Factory.ViewModelFactory
import com.example.githubsampleapplication.R
import com.example.githubsampleapplication.RepoAdapter
import com.example.githubsampleapplication.databinding.ActivityMainBinding
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
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
        mainActivityViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

        mainActivityViewModel.dataResponse.observe(this, Observer {

            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.visibility = View.GONE;
            Log.d(TAG, it.toString())
            if (!it.isNullOrEmpty()) {
                val adapter = RepoAdapter(this@MainActivity, it)
                adapter.setHasStableIds(true)
                binding.repoRecyclerview.adapter = adapter
            }

        })
    }

    public override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }
}
