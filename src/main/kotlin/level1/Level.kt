package level1

data class Economist(val Basket: Basket, val id: Int) {

}
data class Basket(val values: List<Int>) {
    fun getValueSum(): Int {
        return values.sum();
    }
}

fun solve(input: String): String {

    val lines = input.trimEnd('\n').split(Regex("\r?\n"))
    val dataLines = lines.subList(1, lines.size).map {
        it.split(" ").map {
            it.toInt()
        }
    }.map {
        Economist(Basket(it.subList(1, it.size)), it[0])
    }

//    return dataLines.map {
//        it.subList(1, it.size).sum()
//    }.joinToString('\n'.toString())

    return ""
}