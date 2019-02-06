package com.kk.qiitaclient.kotlinstartbookapp.qiitaclient

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.model.Article
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.view.ArticleView

class ArticleListAdapter(private val context: Context): BaseAdapter() {

    var articles: List<Article> = emptyList()

    override fun getCount(): Int {
        return articles.count()
    }

    override fun getItem(position: Int): Any {
        return articles[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View =
        // エルビス演算子を用いる
        // convertViewがnullだった場合は新たにArticleViewのインスタンスを生成する
        // .apply()はKotlin標準ライブラリで提供されている拡張関数
        // setArticle()のメンバへデリファレンスする手間を省いている
        // apply()でArticleViewのメソッドへアクセスできる
        ((convertView) as? ArticleView) ?: ArticleView(context).apply {
            setArticle(articles[position])
    }
}