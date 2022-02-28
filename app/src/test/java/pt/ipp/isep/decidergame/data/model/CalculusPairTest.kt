package pt.ipp.isep.decidergame.data.model

import org.junit.Assert.*
import org.junit.Test

class CalculusPairTest {

    @Test
    fun testGeneratePair() {
        val res = generatePair()
        val op1 = res.first
        val op2 = res.second
        fun rangeForOperation(op: Operation): IntRange = if (op != Operation.DIV) (0..15) else (1..15)
        assertTrue(op1.value in rangeForOperation(op1.operation))
        assertTrue(op2.value in rangeForOperation(op2.operation))
    }

}