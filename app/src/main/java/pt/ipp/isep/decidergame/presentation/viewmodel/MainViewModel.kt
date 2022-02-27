package pt.ipp.isep.decidergame.presentation.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pt.ipp.isep.decidergame.*
import pt.ipp.isep.decidergame.INITIAL_SCORE
import pt.ipp.isep.decidergame.LEFT_BUTTON
import pt.ipp.isep.decidergame.MOVE_TIMER_INTERVAL
import pt.ipp.isep.decidergame.MOVE_TIMER_TIMEOUT
import pt.ipp.isep.decidergame.data.model.Calculus
import pt.ipp.isep.decidergame.data.model.generatePair
import pt.ipp.isep.decidergame.data.persistence.model.Record
import pt.ipp.isep.decidergame.data.persistence.repository.RecordRepository

class MainViewModel(private val repository: RecordRepository) : ViewModel() {

    /* GAME DATA */
    private val _scoreLD = MutableLiveData(INITIAL_SCORE)
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

    private var scorePeak: Int = INITIAL_SCORE
    fun scorePeak() = scorePeak

    private var numMoves: Int = INITIAL_NUM_MOVES
    fun numMoves() = numMoves
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
        _scoreLD.postValue(INITIAL_SCORE)
        startMoveTimer(MOVE_TIMER_TIMEOUT_INI)
        _gameStateLD.postValue(GAME_STARTED)
        scorePeak = INITIAL_SCORE
        numMoves = INITIAL_NUM_MOVES
    }

    private fun stopGame() {
        _gameStateLD.postValue(GAME_STOPPED)
        moveTimer?.cancel()
        _moveTimerLD.postValue(0)
        _scoreLD.postValue(INITIAL_SCORE)
    }

    fun chooseOption(button: Int) {
        val score = scoreLD.value ?: return
        val calculus = if (button == LEFT_BUTTON) calculusPairLD.value?.first else calculusPairLD.value?.second
        val newScore = calculus?.calculate(score) ?: return
        if (!checkScore(newScore)) { return }
        numMoves++
        if (newScore > scorePeak) { scorePeak = newScore }
        _scoreLD.postValue(newScore)
        _calculusPairLD.postValue(generatePair())
        startMoveTimer(MOVE_TIMER_TIMEOUT)
    }

    private fun checkScore(newScore: Int): Boolean {
        if (newScore <= SCORE_BOTTOM_LIMIT) {
            _scoreLD.postValue(SCORE_BOTTOM_LIMIT)
            gameOver()
            return false
        }
        if (newScore > SCORE_TOP_LIMIT) {
            _scoreLD.postValue(newScore)
            gameOver()
            return false
        }
        return true
    }

    private fun gameOver() {
        moveTimer?.cancel()
        _moveTimerLD.postValue(0)
        _gameStateLD.postValue(GAME_OVER)
        gameTime = System.currentTimeMillis() - gameStartTime
        saveResults()
    }

    private fun saveResults() {
        if (numMoves == 0) return
        val gTime = gameTime ?: return
        val record = Record(
            System.currentTimeMillis(),
            gTime,
            numMoves,
            scorePeak
        )
        viewModelScope.launch { repository.insert(record) }
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

class MainViewModelFactory(private val repository: RecordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}