package com.example.wethertestapp

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.wethertestapp.databinding.ActivityMainBinding
import com.example.wethertestapp.databinding.AlertDialogBinding
import com.example.wethertestapp.service.OpenWeatherMapApiHelper
import com.example.wethertestapp.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    lateinit var mViewModel: MainActivityViewModel
    lateinit var mBinding: ActivityMainBinding
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mViewModel.initialize(this)
        mViewModel.getWeather().observe(this, {
            if(it.isNotEmpty()) {
                mBinding.weatherInfo = it[0]
                mBinding.imageView
                OpenWeatherMapApiHelper.loadIcon(mBinding.imageView, applicationContext, it[0].iconUrl)
                mSwipeRefreshLayout.isRefreshing = false
            }
        })

        initRefreshLayout()
        mViewModel.loadWeather()

        mViewModel.getErrorHandler().observe(this, Observer {
            if(it == 1)
                showAlertDialog("Please turn on location")
            else if(it == 2)
                showAlertDialog("Please check internet connection")
        })
    }

    private fun showAlertDialog(msg: String) {
        AlertDialog.Builder(this)
            .setMessage(msg)
            .setTitle("Error!")
            .setNegativeButton("CLOSE", null)
            .create().show()
    }

    //Animated loading icon at the top
    private fun initRefreshLayout() {
        mSwipeRefreshLayout = mBinding.swipeRefresher
        mSwipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
        mSwipeRefreshLayout.isRefreshing = true
        mSwipeRefreshLayout.setOnRefreshListener(OnRefreshListener {
            mViewModel.loadWeather()
        })
    }
}