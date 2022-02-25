package pt.ipp.isep.decidergame.data.model

enum class Operation {
    SUM {
        override fun op(val1: Int, val2: Int) = val1 + val2
    },
    SUB {
        override fun op(val1: Int, val2: Int) = val1 - val2
    },
    PROD {
        override fun op(val1: Int, val2: Int) = val1 * val2
    },
    DIV {
        override fun op(val1: Int, val2: Int) = val1 / val2
    };

    abstract fun op(val1: Int, val2: Int): Int
}