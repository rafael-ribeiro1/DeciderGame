package pt.ipp.isep.decidergame.presentation

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pt.ipp.isep.decidergame.INITIAL_SCORE
import pt.ipp.isep.decidergame.R
import pt.ipp.isep.decidergame.data.model.Operation
import pt.ipp.isep.decidergame.data.persistence.AppDatabase
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MainActivityTest : TestCase() {

    //private lateinit var db: AppDatabase

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    /*@Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }*/

    @Test
    fun testComponentsDisplayed() {
        onView(withId(R.id.btn_start)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_left)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_right)).check(matches(isDisplayed()))
    }

    @Test
    fun testTimeLeftIsDisplayedAfterStartGame() {
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.tv_time_left)).check(matches(isDisplayed()))
    }

    @Test
    fun testInitialScoreAfterStartGame() {
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(withText("$INITIAL_SCORE")))
    }

    @Test
    fun testOperationsDefinedAfterStartGame() {
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.btn_left)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_left))
            .check(matches(not(withText(Operation.values().joinToString(separator = "")))))
        onView(withId(R.id.btn_right)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_right))
            .check(matches(not(withText(Operation.values().joinToString(separator = "")))))
    }

    @Test
    fun testInitialScoreAfterStopGame() {
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(withText("$INITIAL_SCORE")))
    }

    @Test
    fun testOpenRankingsActivity() {
        init()
        onView(withId(R.id.ranking_menu_item)).perform(click())
        intended(hasComponent(RankingsActivity::class.java.name))
        release()
    }

}