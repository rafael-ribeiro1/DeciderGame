package pt.ipp.isep.decidergame.data.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Record")
data class Record (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "datetime")
    val datetime: Long,
    @ColumnInfo(name = "game_time")
    val gameTime: Long,
    @ColumnInfo(name = "num_moves")
    val numMoves: Int,
    @ColumnInfo(name = "score_peak")
    val scorePeak: Int,
)