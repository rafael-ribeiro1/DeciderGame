package pt.ipp.isep.decidergame.presentation.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pt.ipp.isep.decidergame.*
import pt.ipp.isep.decidergame.INITIAL_VALUE
import pt.ipp.isep.decidergame.LEFT_BUTTON
import pt.ipp.isep.decidergame.MOVE_TIMER_INTERVAL
import pt.ipp.isep.decidergame.MOVE_TIMER_TIMEOUT
import pt.ipp.isep.decidergame.data.model.Calculus
import pt.ipp.isep.decidergame.data.model.generatePair

class MainViewModel: ViewModel() {

    /* GAME DATA */
    private val _scoreLD = MutableLiveData(INITIAL_VALUE)
    val scoreLD: LiveData<Int> = _scoreLD

    private var moveTimer: CountDownTimer? = null
    private val _moveTimerLD = MutableLiveData<Long>()
    val moveTimerLD: LiveData<Long> = _moveTimerLD

    private val _calculusPairLD = MutableLiveData<Pair<Calculus, Calculus>>()
    val calculusPairLD: LiveData<Pair<Calculus, Calculus>> = _calculusPairLD

    private val _gameStateLD = MutableLiveData(GAME_STOPPED)
    val gameStateLD: LiveData<Int> = _gameStateLD
    /* GAME DATA - END */

    /* GAME RESULTS */
    private var gameStartTime: Long = System.currentTimeMillis()
    private var gameTime: Long? = null
    fun gameTime() = gameTime

    // TODO: Implement score peak and number of moves
    /* GAME RESULT - END */

    fun startOrStopGame() {
        if (gameStateLD.value == GAME_STARTED) {
            stopGame()
        } else {
            startGame()
        }
    }

    private fun startGame() {
        _calculusPairLD.postValue(generatePair())
        gameStartTime = System.currentTimeMillis()
        _scoreLD.postValue(INITIAL_VALUE)
        startMoveTimer(MOVE_TIMER_TIMEOUT_INI)
        _gameStateLD.postValue(GAME_STARTED)
    }

    private fun stopGame() {
        _gameStateLD.postValue(GAME_STOPPED)
        moveTimer?.cancel()
        _moveTimerLD.postValue(0)
        _scoreLD.postValue(INITIAL_VALUE)
    }

    fun chooseOption(button: Int) {
        val score = scoreLD.value ?: return
        val calculus = if (button == LEFT_BUTTON) calculusPairLD.value?.first else calculusPairLD.value?.second
        val newScore = calculus?.calculate(score) ?: return
        if (newScore <= SCORE_BOTTOM_LIMIT) {
            _scoreLD.postValue(SCORE_BOTTOM_LIMIT)
            gameOver()
            return
        }
        _scoreLD.postValue(newScore)
        _calculusPairLD.postValue(generatePair())
        startMoveTimer(MOVE_TIMER_TIMEOUT)
    }

    private fun gameOver() {
        moveTimer?.cancel()
        _moveTimerLD.postValue(0)
        _gameStateLD.postValue(GAME_OVER)
        gameTime = System.currentTimeMillis() - gameStartTime
    }

    private fun startMoveTimer(time: Long) {
        moveTimer?.cancel()
        moveTimer = object : CountDownTimer(time, MOVE_TIMER_INTERVAL) {
            override fun onTick(p0: Long) {
                _moveTimerLD.postValue(p0)
            }
            override fun onFinish() {
                gameOver()
            }
        }
        moveTimer?.start()
    }

}