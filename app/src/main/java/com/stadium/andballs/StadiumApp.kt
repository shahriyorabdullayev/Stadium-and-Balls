package com.stadium.andballs

import android.app.Application
import com.stadium.andballs.utils.Constants.KEY_LANGUAGE
import com.stadium.andballs.utils.Constants.RU
import dev.b3nedikt.app_locale.AppLocale
import dev.b3nedikt.reword.RewordInterceptor
import dev.b3nedikt.viewpump.ViewPump
import java.util.*

class StadiumApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ViewPump.init(RewordInterceptor)

        if (SharedPref(this).getString(KEY_LANGUAGE) == RU) {
            AppLocale.desiredLocale = Locale(RU)
        }
    }
}