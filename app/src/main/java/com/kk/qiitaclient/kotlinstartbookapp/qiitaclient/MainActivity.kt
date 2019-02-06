package com.kk.qiitaclient.kotlinstartbookapp.qiitaclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.model.Article
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.model.User

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listAdapter = ArticleListAdapter(applicationContext)
        listAdapter.articles = listOf(dummyArticle("Kotlin入門", "たろう"),
            dummyArticle("Java入門", "じろう"))

        val listView: ListView = findViewById<ListView>(R.id.list_view)
        listView.adapter = listAdapter
    }

    /**
     * ダミーの記事を返す
     * @param title    記事タイトル
     * @param userName ユーザー名
     * @return ダミー記事
     */
    private fun dummyArticle(title: String, userName: String): Article =
            Article(id = "",
                title = title,
                url = "https://kotlinlang.org/",
                user = User(id = "", name = userName, profileImageUrl = ""))
}
