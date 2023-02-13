package com.stadium.andballs.ui

import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.ViewPumpAppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.stadium.andballs.R
import com.stadium.andballs.SharedPref
import com.stadium.andballs.service.SoundService
import com.stadium.andballs.utils.Constants.KEY_LANGUAGE
import com.stadium.andballs.utils.Constants.KEY_MUSIC
import dev.b3nedikt.app_locale.AppLocale

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val defaultLocale = Resources.getSystem().configuration.locale.toString().substring(0, 2)
        if (SharedPref(this).getString(KEY_LANGUAGE)!! == "") {
            SharedPref(this).saveString(KEY_LANGUAGE, defaultLocale)
        }

        if (SharedPref(this).getBoolean(KEY_MUSIC)) {
            startService(Intent(this, SoundService::class.java))
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.gameFragment -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.color_game_status)
                }
                else -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.color_all_status)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, SoundService::class.java))
    }

    private val appCompatDelegate: AppCompatDelegate by lazy {
        ViewPumpAppCompatDelegate(
            baseDelegate = super.getDelegate(),
            baseContext = this,
            wrapContext = AppLocale::wrap
        )
    }

    override fun getDelegate(): AppCompatDelegate {
        return appCompatDelegate
    }
}