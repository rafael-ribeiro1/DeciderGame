package pt.ipp.isep.decidergame.presentation.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import pt.ipp.isep.decidergame.data.persistence.model.Record
import pt.ipp.isep.decidergame.data.persistence.repository.RecordRepository
import pt.ipp.isep.decidergame.getOrAwaitValue

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class RankingsViewModelUnitTest : TestCase() {

    private lateinit var viewModel: RankingsViewModel
    private lateinit var repoMock: RecordRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        repoMock = mock()
        viewModel = RankingsViewModel(repoMock)
    }

    @Test
    fun testGameTimeRanking() = runBlocking {
        val ranking = listOf(
            Record(3, 5000, 3, 25),
            Record(1, 3000, 3, 25),
            Record(2, 2000, 3, 25)
        )
        whenever(repoMock.gameTimeRanking()).thenReturn(ranking)
        viewModel.gameTimeRanking()
        val res = viewModel.rankingLD.getOrAwaitValue()
        verify(repoMock, times(1)).gameTimeRanking()
        assertArrayEquals(ranking.toTypedArray(), res.toTypedArray())
    }

    @Test
    fun testNumMovesRanking() = runBlocking {
        val ranking = listOf(
            Record(3, 5000, 25, 25),
            Record(1, 3000, 11, 25),
            Record(2, 2000, 3, 25)
        )
        whenever(repoMock.numMovesRanking()).thenReturn(ranking)
        viewModel.numMovesRanking()
        val res = viewModel.rankingLD.getOrAwaitValue()
        verify(repoMock, times(1)).numMovesRanking()
        assertArrayEquals(ranking.toTypedArray(), res.toTypedArray())
    }

    @Test
    fun testScorePeakRanking() = runBlocking {
        val ranking = listOf(
            Record(3, 5000, 25, 1200),
            Record(1, 3000, 11, 150),
            Record(2, 2000, 3, 90)
        )
        whenever(repoMock.scorePeakRanking()).thenReturn(ranking)
        viewModel.scorePeakRanking()
        val res = viewModel.rankingLD.getOrAwaitValue()
        verify(repoMock, times(1)).scorePeakRanking()
        assertArrayEquals(ranking.toTypedArray(), res.toTypedArray())
    }

}