package com.kk.qiitaclient.kotlinstartbookapp.qiitaclient

import android.content.Context
import android.support.annotation.IdRes
import android.view.View
import android.widget.Toast

// NullableでなくNonNullにするために、findViewByの使用を避けて、lazy関数を使う
// lazy: Kotlin標準ライブラリで提供されている
// 委譲プロパティの委譲先になれるオブジェクトを生成して返す
// 拡張関数
// lazyの返す型T
fun <T: View> View.bindView(@IdRes id: Int): Lazy<T> = lazy {
    findViewById<T>(id)
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}