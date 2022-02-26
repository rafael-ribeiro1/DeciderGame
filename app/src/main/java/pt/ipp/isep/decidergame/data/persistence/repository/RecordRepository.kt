package pt.ipp.isep.decidergame.data.persistence.repository

import kotlinx.coroutines.flow.Flow
import pt.ipp.isep.decidergame.data.persistence.AppDatabase
import pt.ipp.isep.decidergame.data.persistence.dao.RecordDao
import pt.ipp.isep.decidergame.data.persistence.model.Record

class RecordRepository(private val recordDao: RecordDao) {

    fun insert(record: Record) {
        AppDatabase.databaseWriterExecutor
            .execute {
                recordDao.insert(record)
            }
    }

    val gameTimeRanking: Flow<List<Record>> = recordDao.getGameTimeRanking()
    val numMovesRanking: Flow<List<Record>> = recordDao.getNumMovesRanking()
    val scorePeakRanking: Flow<List<Record>> = recordDao.getScorePeakRanking()

}