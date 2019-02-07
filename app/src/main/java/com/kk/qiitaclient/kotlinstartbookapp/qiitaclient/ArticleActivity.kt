package com.kk.qiitaclient.kotlinstartbookapp.qiitaclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.model.Article
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.view.ArticleView

class ArticleActivity: AppCompatActivity() {

    companion object {
        // constをつけると@JvmFieldと同様に JavaのStaticとなる
        private const val ARTICLE_EXTRA: String = "article"

        // アクティビティを起動するIntentにExtraを不可するため
        fun intent(context: Context, article: Article): Intent =
                Intent(context, ArticleActivity::class.java)
                    .putExtra(ARTICLE_EXTRA, article)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_article)

        val articleView = findViewById<ArticleView>(R.id.article_view)
        val webView = findViewById<WebView>(R.id.web_view)

        // Intentに付加されている記事オブジェクトを取得する
        val article: Article = intent.getParcelableExtra(ARTICLE_EXTRA)
        articleView.setArticle(article)
        webView.loadUrl(article.url)
    }
}