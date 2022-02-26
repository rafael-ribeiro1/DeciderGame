package pt.ipp.isep.decidergame

import android.app.Application
import pt.ipp.isep.decidergame.data.persistence.AppDatabase
import pt.ipp.isep.decidergame.data.persistence.repository.RecordRepository

class DeciderApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val recordRepository by lazy { RecordRepository(database.recordDao()) }
}