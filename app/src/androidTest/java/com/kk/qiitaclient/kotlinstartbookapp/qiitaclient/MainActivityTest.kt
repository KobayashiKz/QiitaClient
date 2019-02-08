package com.kk.qiitaclient.kotlinstartbookapp.qiitaclient

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    // JUnitがJavaにおけるフィールドを要求しているため@JvmFieldをつける
    // ActivityTestRuleによってActivity単体で試験できる
    @JvmField
    @Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun 各ビューが表示されていること_ただしプログレスバーは非表示() {
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

    private fun isNotDisplayed(): Matcher<View> = not(isDisplayed())
}