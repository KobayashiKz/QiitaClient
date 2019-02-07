package com.kk.qiitaclient.kotlinstartbookapp.qiitaclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.client.ArticleClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listAdapter = ArticleListAdapter(applicationContext)

        val listView: ListView = findViewById<ListView>(R.id.list_view)
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


        // 検索テキストフィールド
        val queryEditText = findViewById<EditText>(R.id.query_edit_text)
        // 検索ボタン
        val searchButton = findViewById<Button>(R.id.search_button)

        // 検索ボタンタップしたらRxAndroidにより非同期でコールバックをメインスレッドで受け取る
        searchButton.setOnClickListener {
            // クエリ名を指定してObservable<List<Article>>を取得する
            articleClient.search(queryEditText.text.toString())
                // 結果（ストリーム）の生成を行う部分を別スレッドで行うよう設定
                .subscribeOn(Schedulers.io())
                // コールバック処理がメインスレッドで動く
                .observeOn(AndroidSchedulers.mainThread())
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
