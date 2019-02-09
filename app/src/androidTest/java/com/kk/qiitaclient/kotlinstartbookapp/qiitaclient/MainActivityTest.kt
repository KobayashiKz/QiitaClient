package com.kk.qiitaclient.kotlinstartbookapp.qiitaclient

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.kk.qiitaclient.kotlinstartbookapp.qiitaclient.client.ArticleClient
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import rx.Observable

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    // JUnitがJavaにおけるフィールドを要求しているため@JvmFieldをつける
    // ActivityTestRuleによってActivity単体で試験できる
    @JvmField
    @Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun `各ビューが表示されていること_ただしプログレスバーは非表示`() {
        // onView()/withId/check などはEspressoが提供するAPI
        // onView: Espressoのstaticメソッド
        // onView(withId(R.id.list_view)) : テスト対象を操作や検証できるようにしている
        // .check(): 検証
        // matches(isDisplayed()): 表示中かどうか
        onView(withId(R.id.list_view)).check(matches(isDisplayed()))
        onView(withId(R.id.query_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.search_button)).check(matches(isDisplayed()))

        onView(withId(R.id.progress_bar)).check(matches(isNotDisplayed()))
    }

    /**
     * 非表示かどうか
     */
    private fun isNotDisplayed(): Matcher<View> = not(isDisplayed())

    // Dagger2によるモックnoの差し込みを行うと綺麗
    // Dagger2の機能を利用してモックオブジェクトをインジェクトする
    @Test
    fun `検索ボタンがタップされたら入力されたクエリ文字列で記事検索APIを叩くこと`() {
        val articleClient = mock(ArticleClient::class.java).apply {
            `when`(search("user:ngsw_taro")).thenReturn(Observable.just(listOf()))
        }

        activityTestRule.activity.articleClient = articleClient

        onView(withId(R.id.query_edit_text)).perform(typeText("user:ngsw_taro"))
        onView(withId(R.id.search_button)).perform(click())

        verify(articleClient).search("user:ngsw_taro")
    }

}