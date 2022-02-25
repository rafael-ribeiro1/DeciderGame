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
import pt.ipp.isep.decidergame.RIGHT_BUTTON
import pt.ipp.isep.decidergame.data.model.Calculus
import pt.ipp.isep.decidergame.data.model.generatePair

class MainViewModel: ViewModel() {

    private val _scoreLD = MutableLiveData(INITIAL_VALUE)
    val scoreLD: LiveData<Int> = _scoreLD

    private var moveTimer: CountDownTimer? = null
    private val _moveTimerLD = MutableLiveData<Long>()
    val moveTimerLD: LiveData<Long> = _moveTimerLD

    private val _calculusPairLD = MutableLiveData<Pair<Calculus, Calculus>>()
    val calculusPairLD: LiveData<Pair<Calculus, Calculus>> = _calculusPairLD

    private var gameStartTime: Long? = null

    private val _activeLD = MutableLiveData(false)
    val activeLD: LiveData<Boolean> = _activeLD
    private var isGameActive = false

    fun startOrStopGame() {
        if (activeLD.value ?: return) {
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
        _activeLD.postValue(true)
    }

    private fun stopGame() {
        _activeLD.postValue(false)
        moveTimer?.cancel()
        _moveTimerLD.postValue(0)
        _scoreLD.postValue(INITIAL_VALUE)
    }

    fun chooseOption(button: Int) {
        val score = scoreLD.value ?: return
        val calculus = if (button == LEFT_BUTTON) calculusPairLD.value?.first else calculusPairLD.value?.second
        val newScore = calculus?.calculate(score) ?: return
        _scoreLD.postValue(newScore)
        _calculusPairLD.postValue(generatePair())
        startMoveTimer(MOVE_TIMER_TIMEOUT)
    }

    private fun startMoveTimer(time: Long) {
        moveTimer?.cancel()
        moveTimer = object : CountDownTimer(time, MOVE_TIMER_INTERVAL) {
            override fun onTick(p0: Long) {
                _moveTimerLD.postValue(p0)
            }
            override fun onFinish() {
                _moveTimerLD.postValue(0)
                _activeLD.postValue(false)
                // TODO: GameOver
            }
        }
        moveTimer?.start()
    }

}