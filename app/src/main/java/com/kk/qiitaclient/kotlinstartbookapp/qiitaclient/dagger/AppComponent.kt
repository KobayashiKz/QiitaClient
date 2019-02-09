package com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.dagger

import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.MainActivity
import dagger.Component
import javax.inject.Singleton

// 依存性注入先を指定. 使用するモジュールを宣言

// modulesにはクラスリストを渡す
@Component(modules = [ClientModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}


