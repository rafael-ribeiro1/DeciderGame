package pt.ipp.isep.decidergame.presentation.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pt.ipp.isep.decidergame.data.persistence.model.Record
import pt.ipp.isep.decidergame.data.persistence.repository.RecordRepository

class RankingsViewModel(private val repository: RecordRepository) : ViewModel() {

    private val _rankingLD = MutableLiveData<List<Record>>()
    val rankingLD: LiveData<List<Record>> = _rankingLD

    fun gameTimeRanking() {
        viewModelScope.launch {
            _rankingLD.postValue(repository.gameTimeRanking())
        }
    }

    fun numMovesRanking() {
        viewModelScope.launch {
            _rankingLD.postValue(repository.numMovesRanking())
        }
    }

    fun scorePeakRanking() {
        viewModelScope.launch {
            _rankingLD.postValue(repository.scorePeakRanking())
        }
    }

}

class RankingsViewModelFactory(private val repository: RecordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RankingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RankingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}