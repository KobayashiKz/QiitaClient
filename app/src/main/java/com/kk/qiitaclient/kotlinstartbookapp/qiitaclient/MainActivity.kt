package com.kk.qiitaclient.kotlinstartbookapp.qiitaclient

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.client.ArticleClient
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

// RxAndroidを使用する上で、Activityのライフサイクルを考慮しないといけない
// Activityが破棄されることでメモリリークやエラーが発生する場合も
// RxLifecycle: ライフサイクルの面倒を見てくれるライブラリ
// RxAppCompatActivityを継承する
class MainActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById<ListView>(R.id.list_view)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val queryEditText = findViewById<EditText>(R.id.query_edit_text)
        val searchButton = findViewById<Button>(R.id.search_button)

        val listAdapter = ArticleListAdapter(applicationContext)
        listView.adapter = listAdapter
        // ラムダ式
        listView.setOnItemClickListener { parent, view, position, id ->
            val article = listAdapter.articles[position]
            // 拡張関数letを入れて画面遷移処理
            startActivity(ArticleActivity.intent(this, article))
        }

        // Gson
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        // レトロフィット
        val retrofit = Retrofit.Builder()
            // エンドポイントのベースとなるURLを指定
            .baseUrl("https://qiita.com")
            // レスポンスからオブジェクトへのコンバータファクトリを設定
            .addConverterFactory(GsonConverterFactory.create(gson))
            // RetrofitでRxAndroidを使用できるように設定
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
        val articleClient = retrofit.create(ArticleClient::class.java)

        // 検索ボタンタップしたらRxAndroidにより非同期でコールバックをメインスレッドで受け取る
        searchButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            // クエリ名を指定してObservable<List<Article>>を取得する
            articleClient.search(queryEditText.text.toString())
                // 結果（ストリーム）の生成を行う部分を別スレッドで行うよう設定
                .subscribeOn(Schedulers.io())
                // コールバック処理がメインスレッドで動く
                .observeOn(AndroidSchedulers.mainThread())
                // ストリームが終端に近づいた時の処理
                .doAfterTerminate {
                    progressBar.visibility = View.GONE
                }
                // Observableに対する拡張関数でライフサイクルを考慮した挙動を
                .bindToLifecycle(this)
                // 結果の受け取りコールバックをラムダ式で渡している
                .subscribe({
                    queryEditText.text.clear()
                    listAdapter.articles = it
                    listAdapter.notifyDataSetChanged()
                }, {
                    toast("error: $it")
                })
        }
    }
}
