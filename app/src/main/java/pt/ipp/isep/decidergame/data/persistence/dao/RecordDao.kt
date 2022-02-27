package pt.ipp.isep.decidergame.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.ipp.isep.decidergame.data.persistence.model.Record

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(record: Record)

    @Query("SELECT * FROM Record ORDER BY game_time DESC, datetime ASC LIMIT 10")
    suspend fun getGameTimeRanking(): List<Record>

    @Query("SELECT * FROM Record ORDER BY num_moves DESC, datetime ASC LIMIT 10")
    suspend fun getNumMovesRanking(): List<Record>

    @Query("SELECT * FROM Record ORDER BY score_peak DESC, datetime ASC LIMIT 10")
    suspend fun getScorePeakRanking(): List<Record>

}