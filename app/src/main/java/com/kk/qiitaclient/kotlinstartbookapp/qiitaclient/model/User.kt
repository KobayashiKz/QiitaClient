package com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.model

import android.os.Parcel
import android.os.Parcelable

// Parcelable: 状態を一時的に保存したいオブジェクトが実装するインターフェース
// インテントにつめて受け渡しができる
// Activity間の値の受け渡しが簡単にできる
data class User(val id: String,
                val name: String,
                val profileImageUrl: String): Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        // 復元用のCreator
        // Javaにおけるstaticフィールドは、companion objectと@JvmFieldで実現している
        // @JvmField: Javaにおけるフィールドとしてコンパイルされるようになる
        // これを付与しなかった場合、User.Companion.getCREATOR()のように取得しなければならない
        // 付与することでUser.CREATORのように直接アクセスできる
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = source.run {
                User(readString(), readString(), readString())
            }

            // arrayOfNull(): Nullで満たされた指定サイズ分のリスト
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        // 拡張関数run()は標準ライブラリが提供する任意の型に対する拡張関数
        // apply()に似ているが、動きは以下と同じ
        // public inline fun<T, R> T.run(block: T.() -> R): R = block()
        dest.run {
            writeString(id)
            writeString(name)
            writeString(profileImageUrl)
        }
    }
}