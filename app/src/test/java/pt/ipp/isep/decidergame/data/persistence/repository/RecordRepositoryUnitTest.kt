package pt.ipp.isep.decidergame.data.persistence.repository

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import pt.ipp.isep.decidergame.data.persistence.dao.RecordDao
import pt.ipp.isep.decidergame.data.persistence.model.Record

class RecordRepositoryUnitTest {

    companion object {
        lateinit var daoMock: RecordDao
        lateinit var repository: RecordRepository

        @BeforeClass
        @JvmStatic
        fun setup() {
            daoMock = mock()
            repository = RecordRepository(daoMock)
        }
    }

    @Test
    fun testInsert() = runBlocking {
        val record = Record(System.currentTimeMillis(), 3000, 3, 25)
        repository.insert(record)
        verify(daoMock, times(1)).insert(record)
    }

    @Test
    fun testGameTimeRankingReturnsList() = runBlocking {
        val ranking = listOf(
            Record(3, 5000, 3, 25),
            Record(1, 3000, 3, 25),
            Record(2, 2000, 3, 25)
        )
        whenever(daoMock.getGameTimeRanking()).thenReturn(ranking)
        val res = repository.gameTimeRanking()
        verify(daoMock, times(1)).getGameTimeRanking()
        assertArrayEquals(ranking.toTypedArray(), res.toTypedArray())
    }

    @Test
    fun testNumMovesRankingReturnsList() = runBlocking {
        val ranking = listOf(
            Record(3, 5000, 25, 25),
            Record(1, 3000, 11, 25),
            Record(2, 2000, 3, 25)
        )
        whenever(daoMock.getNumMovesRanking()).thenReturn(ranking)
        val res = repository.numMovesRanking()
        verify(daoMock, times(1)).getNumMovesRanking()
        assertArrayEquals(ranking.toTypedArray(), res.toTypedArray())
    }

    @Test
    fun testScorePeakRankingReturnsList() = runBlocking {
        val ranking = listOf(
            Record(3, 5000, 25, 1200),
            Record(1, 3000, 11, 150),
            Record(2, 2000, 3, 90)
        )
        whenever(daoMock.getScorePeakRanking()).thenReturn(ranking)
        val res = repository.scorePeakRanking()
        verify(daoMock, times(1)).getScorePeakRanking()
        assertArrayEquals(ranking.toTypedArray(), res.toTypedArray())
    }

}