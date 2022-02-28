package pt.ipp.isep.decidergame.data.model

import org.junit.Assert.*
import org.junit.Test

class CalculusUnitTest {

    @Test
    fun testCalculateSum() {
        val calculus = Calculus(Operation.SUM, 5)
        val res = calculus.calculate(10)
        val expected = 15
        assertEquals(expected, res)
    }

    @Test
    fun testCalculateSub() {
        val calculus = Calculus(Operation.SUB, 5)
        val res = calculus.calculate(10)
        val expected = 5
        assertEquals(expected, res)
    }

    @Test
    fun testCalculateProd() {
        val calculus = Calculus(Operation.PROD, 5)
        val res = calculus.calculate(10)
        val expected = 50
        assertEquals(expected, res)
    }

    @Test
    fun testCalculateDiv() {
        val calculus = Calculus(Operation.DIV, 5)
        val res = calculus.calculate(10)
        val expected = 2
        assertEquals(expected, res)
    }

    @Test
    fun testToStringSumFormat() {
        val calculus = Calculus(Operation.SUM, 5)
        val res = calculus.toString()
        val expected = "+5"
        assertEquals(expected, res)
    }

    @Test
    fun testToStringSubFormat() {
        val calculus = Calculus(Operation.SUB, 5)
        val res = calculus.toString()
        val expected = "-5"
        assertEquals(expected, res)
    }

    @Test
    fun testToStringProdFormat() {
        val calculus = Calculus(Operation.PROD, 5)
        val res = calculus.toString()
        val expected = "×5"
        assertEquals(expected, res)
    }

    @Test
    fun testToStringDivFormat() {
        val calculus = Calculus(Operation.DIV, 5)
        val res = calculus.toString()
        val expected = "÷5"
        assertEquals(expected, res)
    }

}