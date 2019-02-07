package com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.client

import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.model.Article
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface ArticleClient {

    // RxAndroid: RxJavanoのAndroidに特化したライブラリ
    // 非同期処理をRxAndroidで行うことで扱いが統一的で簡単になる
    // WebAPIへリクエストを投げてコールバックが返ってくるまで非同期で行い、UIスレッドで描画更新する

    // Retrofitが提供する@GETと@Queryアノテーション
    // @GET: 引数に指定したエンドポイントへのGETメソッド
    // @Query: "query"のクエリ名で、query: Stringがクエリパラメータ
    // 戻り値のObservable: RxAndroidが提供するクラス
    // Observableを購読（subscribe）することで結果を受け取れる
    // 結果はObservableに渡しているList<Article>型となる
    @GET("/api/v2/items")
    fun search(@Query("query") query: String): Observable<List<Article>>
}