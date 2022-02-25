package pt.ipp.isep.decidergame.data.model

// TODO: Improve generation
fun generatePair() : Pair<Calculus, Calculus> {
    val op1 = Operation.values().random()
    val val1 = if (op1 != Operation.DIV) (0..15).random() else (1..15).random()
    val op2 = Operation.values().random()
    val val2 = if (op2 != Operation.DIV) (0..15).random() else (1..15).random()
    return Pair(Calculus(op1, val1), Calculus(op2, val2))
}