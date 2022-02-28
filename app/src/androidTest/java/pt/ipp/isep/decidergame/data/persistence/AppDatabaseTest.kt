package pt.ipp.isep.decidergame.data.persistence

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pt.ipp.isep.decidergame.data.persistence.dao.RecordDao
import pt.ipp.isep.decidergame.data.persistence.model.Record
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : TestCase() {

    private lateinit var db: AppDatabase
    private lateinit var recordDao: RecordDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        recordDao = db.recordDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertRecord() = runBlocking {
        val record = Record(System.currentTimeMillis(), 3000, 3, 25)
        recordDao.insert(record)
        val records = recordDao.getGameTimeRanking()
        val expected = 1
        assertEquals(expected, records.size)
    }

    @Test
    fun testGetGameTimeRankingWithCorrectOrder() = runBlocking {
        recordDao.insert(Record(1, 3000, 3, 25))
        recordDao.insert(Record(2, 2000, 3, 25))
        recordDao.insert(Record(3, 5000, 3, 25))
        val records = recordDao.getGameTimeRanking()
        val expectedSize = 3
        assertEquals(expectedSize, records.size)
        assertEquals(5000, records[0].gameTime)
        assertEquals(3000, records[1].gameTime)
        assertEquals(2000, records[2].gameTime)
    }

    @Test
    fun testGetNumMovesRankingWithCorrectOrder() = runBlocking {
        recordDao.insert(Record(1, 3000, 25, 25))
        recordDao.insert(Record(2, 2000, 3, 25))
        recordDao.insert(Record(3, 5000, 11, 25))
        val records = recordDao.getNumMovesRanking()
        val expectedSize = 3
        assertEquals(expectedSize, records.size)
        assertEquals(25, records[0].numMoves)
        assertEquals(11, records[1].numMoves)
        assertEquals(3, records[2].numMoves)
    }

    @Test
    fun testGetScorePeakRankingWithCorrectOrder() = runBlocking {
        recordDao.insert(Record(1, 3000, 25, 150))
        recordDao.insert(Record(2, 2000, 3, 90))
        recordDao.insert(Record(3, 5000, 11, 1200))
        val records = recordDao.getScorePeakRanking()
        val expectedSize = 3
        assertEquals(expectedSize, records.size)
        assertEquals(1200, records[0].scorePeak)
        assertEquals(150, records[1].scorePeak)
        assertEquals(90, records[2].scorePeak)
    }

}