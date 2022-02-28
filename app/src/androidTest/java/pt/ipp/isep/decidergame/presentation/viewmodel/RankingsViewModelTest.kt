package pt.ipp.isep.decidergame.presentation.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pt.ipp.isep.decidergame.data.persistence.AppDatabase
import pt.ipp.isep.decidergame.data.persistence.model.Record
import pt.ipp.isep.decidergame.data.persistence.repository.RecordRepository
import pt.ipp.isep.decidergame.getOrAwaitValue
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RankingsViewModelTest : TestCase() {

    private lateinit var db: AppDatabase
    private lateinit var viewModel: RankingsViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        val repository = RecordRepository(db.recordDao())
        viewModel = RankingsViewModel(repository)
        runBlocking {
            repository.insert(Record(1, 3000, 25, 150))
            repository.insert(Record(2, 2000, 3, 90))
            repository.insert(Record(3, 5000, 11, 1200))
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testGameTimeRanking() {
        viewModel.gameTimeRanking()
        val res = viewModel.rankingLD.getOrAwaitValue()
        val expectedSize = 3
        assertEquals(expectedSize, res.size)
        assertEquals(5000, res[0].gameTime)
        assertEquals(3000, res[1].gameTime)
        assertEquals(2000, res[2].gameTime)
    }

    @Test
    fun testNunMovesRanking() {
        viewModel.numMovesRanking()
        val res = viewModel.rankingLD.getOrAwaitValue()
        val expectedSize = 3
        assertEquals(expectedSize, res.size)
        assertEquals(25, res[0].numMoves)
        assertEquals(11, res[1].numMoves)
        assertEquals(3, res[2].numMoves)
    }

    @Test
    fun testScorePeakRanking() {
        viewModel.scorePeakRanking()
        val res = viewModel.rankingLD.getOrAwaitValue()
        val expectedSize = 3
        assertEquals(expectedSize, res.size)
        assertEquals(1200, res[0].scorePeak)
        assertEquals(150, res[1].scorePeak)
        assertEquals(90, res[2].scorePeak)
    }

}