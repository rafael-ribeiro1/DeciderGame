package pt.ipp.isep.decidergame.data.persistence.repository

import androidx.annotation.WorkerThread
import pt.ipp.isep.decidergame.data.persistence.AppDatabase
import pt.ipp.isep.decidergame.data.persistence.dao.RecordDao
import pt.ipp.isep.decidergame.data.persistence.model.Record

class RecordRepository(private val recordDao: RecordDao) {

    @WorkerThread
    suspend fun insert(record: Record) {
        recordDao.insert(record)
    }

    suspend fun gameTimeRanking() : List<Record> = recordDao.getGameTimeRanking()
    suspend fun numMovesRanking() : List<Record> = recordDao.getNumMovesRanking()
    suspend fun scorePeakRanking() : List<Record> = recordDao.getScorePeakRanking()

}