package com.kk.qiitaclient.kotlinstartbookapp.qiitaclient

import android.app.Application
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.dagger.AppComponent
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.dagger.DaggerAppComponent

class QiitaClientApp: Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.create()
    }
}