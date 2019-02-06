package com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.R
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.bindView
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.model.Article

class ArticleView: FrameLayout {

    // FrameLayoutの祖先であるViewは多くのコンストラクタを持っていてそれに対応する
    constructor(context: Context?): super(context)

    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?,
                defStyleAttr: Int): super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?,
                defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)

//    var profileImageView: ImageView? = null
    // lazy関数を使用
    val profileImageView: ImageView by bindView<ImageView>(R.id.profile_image_view)
    val titleTextView: TextView by bindView<TextView>(R.id.title_text_view)
    val userNameTextView: TextView by bindView<TextView>(R.id.user_text_name)

    // オブジェクトの初期化
    init {
        LayoutInflater.from(context).inflate(R.layout.view_article, this)
    }

    /**
     * 記事をセットする
     * @param article 記事情報をつめたデータクラス
     */
    fun setArticle(article: Article) {
        titleTextView?.text = article.title
        userNameTextView?.text = article.user.name

        // TODO プロフィール画像セット
        profileImageView.setBackgroundColor(Color.RED)
    }
}