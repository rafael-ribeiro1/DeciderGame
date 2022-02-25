package pt.ipp.isep.decidergame.data.model

enum class Operation {
    SUM {
        override fun op(val1: Int, val2: Int) = val1 + val2
        override fun toString() = "+"
    },
    SUB {
        override fun op(val1: Int, val2: Int) = val1 - val2
        override fun toString() = "-"
    },
    PROD {
        override fun op(val1: Int, val2: Int) = val1 * val2
        override fun toString() = "×"
    },
    DIV {
        override fun op(val1: Int, val2: Int) = val1 / val2
        override fun toString() = "÷"
    };

    abstract fun op(val1: Int, val2: Int): Int
}