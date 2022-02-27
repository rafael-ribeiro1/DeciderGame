package pt.ipp.isep.decidergame.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pt.ipp.isep.decidergame.data.persistence.dao.RecordDao
import pt.ipp.isep.decidergame.data.persistence.model.Record
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val DB_NAME = "decider_database"

@Database(entities = [Record::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }

}