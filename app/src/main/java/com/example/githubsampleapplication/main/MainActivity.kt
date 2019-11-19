package com.example.githubsampleapplication.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.githubsampleapplication.Factory.ViewModelFactory
import com.example.githubsampleapplication.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var mainActivityViewModel : MainActivityViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityViewModel = ViewModelProviders.of(this,viewModelFactory).get(MainActivityViewModel::class.java)
        mainActivityViewModel.dataResponse.observe(this,  Observer {
            progress_circular.visibility = View.GONE
            Log.d(TAG,it.toString())
        })
    }
}
