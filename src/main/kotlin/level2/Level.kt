package level2

import kotlin.math.*


fun solvePerLine(input: String): String {
    val inputSplitted = input.split(" ")
    val actions = mutableListOf<ActionPair>()
    for (i in 1 until inputSplitted.size - 1 step 2) {
        actions.add(ActionPair(inputSplitted[i], inputSplitted[i + 1].toInt()))
    }
    var current = Coordinate(0, 0)
    val max = Array<Int>(4) { it -> 0 } //min x, min y, max x, max y
    actions.forEach { action ->
        repeat(action.times) {
            for (step in action.move) {
                current = current.addMove(step)
                max[0] = min(max[0], current.x)
                max[1] = min(max[1], current.y)
                max[2] = max(max[2], current.x)
                max[3] = max(max[3], current.y)
            }
        }
    }

    return count(actions).toString() + " " + (abs(max[2] - max[0])).times(abs(max[3] - max[1])).toString()
}

fun count(actions: MutableList<ActionPair>): Int {
    return actions.sumOf { it.move.count { it == 'F' } * it.times }

}

fun solve(input: String): String {
    val lines = input.split(Regex("\r?\n"))
    return lines.map { solvePerLine(it) }.joinToString("\n")

}

data class ActionPair(val move: String, val times: Int)

enum class Orientation {
    NORTH,
    EAST,
    SOUTH,
    WEST,
}

data class Coordinate(var x: Int, var y: Int, var orientation: Orientation = Orientation.NORTH) {
    fun addMove(step: Char): Coordinate {
        var o = this.orientation
        var newC = this.copy()

        when (step) {
            'F' -> {
                when (o) {
                    Orientation.NORTH -> newC.y -= 1
                    Orientation.EAST -> newC.x += 1
                    Orientation.SOUTH -> newC.y += 1
                    Orientation.WEST -> newC.x -= 1
                }
            }
            'L' -> {
                var res = o.ordinal - 1
                if (res < 0) {
                    res += 4
                }
                o = Orientation.values()[res]
            }
            'R' -> {
                o = Orientation.values()[(o.ordinal + 1) % 4]
            }
        }
        newC.orientation = o

        return newC
    }

}
