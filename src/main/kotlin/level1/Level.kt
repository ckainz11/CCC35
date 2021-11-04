package level1

data class ActionPair (val move: String, val count: Int,)

fun solve(input: String): String {
    val lines = input.split(Regex("\r?\n"))
    val datalines = lines.map {
        var l = it.split(" ")
        l = l.subList(1, l.size)
        val dataline = mutableListOf<ActionPair>()
        for (i in 0 until l.size-1 step 2) {
            dataline.add(ActionPair(l[i], l[i+1].toInt()))
        }
        dataline
    }


    val x =
        datalines.map {
            it.sumOf { it.move.count { it == 'F' } * it.count }
        }.joinToString("\n")
    return x
}