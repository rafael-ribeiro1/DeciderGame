package pt.ipp.isep.decidergame.presentation.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import pt.ipp.isep.decidergame.*
import pt.ipp.isep.decidergame.data.persistence.repository.RecordRepository

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class MainViewModelUnitTest : TestCase() {

    private lateinit var viewModel: MainViewModel
    private lateinit var repoMock: RecordRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        repoMock = mock()
        viewModel = MainViewModel(repoMock)
    }

    @Test
    fun testStartGame() {
        viewModel.startOrStopGame()
        val score = viewModel.scoreLD.getOrAwaitValue()
        val expectedScore = INITIAL_SCORE
        assertEquals(expectedScore, score)
        val gameState = viewModel.gameStateLD.getOrAwaitValue()
        val expectedGameState = GAME_STARTED
        assertEquals(expectedGameState, gameState)
        val expectedScorePeak = INITIAL_SCORE
        assertEquals(expectedScorePeak, viewModel.scorePeak)
        val expectedNumMoves = INITIAL_NUM_MOVES
        assertEquals(expectedNumMoves, viewModel.numMoves)
    }

    @Test
    fun testStopGameRightAfterStarting() {
        viewModel.startOrStopGame()
        viewModel.startOrStopGame()
        val score = viewModel.scoreLD.getOrAwaitValue()
        val expectedScore = INITIAL_SCORE
        assertEquals(expectedScore, score)
        val gameState = viewModel.gameStateLD.getOrAwaitValue()
        val expectedGameState = GAME_STOPPED
        assertEquals(expectedGameState, gameState)
        val moveTimer = viewModel.moveTimerLD.getOrAwaitValue()
        val expectedMoveTimer = 0L
        assertEquals(expectedMoveTimer, moveTimer)
        val expectedScorePeak = INITIAL_SCORE
        assertEquals(expectedScorePeak, viewModel.scorePeak)
        val expectedNumMoves = INITIAL_NUM_MOVES
        assertEquals(expectedNumMoves, viewModel.numMoves)
    }

    @Test
    fun testChooseLeftOption() {
        chooseOptionTest(LEFT_BUTTON)
    }

    @Test
    fun testChooseRightOption() {
        chooseOptionTest(RIGHT_BUTTON)
    }

    private fun chooseOptionTest(option: Int) {
        viewModel.startOrStopGame()
        val pair = viewModel.calculusPairLD.getOrAwaitValue()
        viewModel.chooseOption(option)
        val score = viewModel.scoreLD.getOrAwaitValue()
        val op = if (option == LEFT_BUTTON) pair.first else pair.second
        val scoreRes = op.calculate(INITIAL_SCORE)
        val expectedScore = if (scoreRes <= 0) 0 else scoreRes
        assertEquals(expectedScore, score)
        val gameState = viewModel.gameStateLD.getOrAwaitValue()
        val expectedGameState = if (score > 0) GAME_STARTED else GAME_OVER
        assertEquals(expectedGameState, gameState)
        val expectedScorePeak = if (score > INITIAL_SCORE) score else INITIAL_SCORE
        assertEquals(expectedScorePeak, viewModel.scorePeak)
        val expectedNumMoves = if (score == 0) 0 else 1
        assertEquals(expectedNumMoves, viewModel.numMoves)
    }

}