package com.example.task_sqb.presentation

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task_sqb.R
import com.example.task_sqb.databinding.ActivityMainBinding
import com.example.task_sqb.presentation.navigation.AppNavigator
import com.example.task_sqb.presentation.screens.main.vm.NetworkChangeReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var appNavigator: AppNavigator
    private var mNetworkReceiver: BroadcastReceiver? = null
    private val binding by viewBinding(ActivityMainBinding::bind, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appNavigator.navigationFlow.onEach { navigation->
            navigation.invoke(findNavController(R.id.nav_host_fragment))
        }.launchIn(lifecycleScope)
        mNetworkReceiver = NetworkChangeReceiver()
        registerNetworkBroadcastForNougat()
    }
    private fun registerNetworkBroadcastForNougat() {
        registerReceiver(
            mNetworkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            registerReceiver(
//                mNetworkReceiver,
//                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
//            )
//        }
    }
    private fun unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterNetworkChanges()
    }
}