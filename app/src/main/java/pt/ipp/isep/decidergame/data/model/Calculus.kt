package pt.ipp.isep.decidergame.data.model

data class Calculus(val operation: Operation, val value: Int) {
    fun calculate(score: Int) = operation.op(score, value)
}
