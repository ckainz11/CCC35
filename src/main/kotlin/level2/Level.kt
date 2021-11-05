package level2


data class Economist(val basket: Basket, val id: Int, val preferences: MutableList<Int> = mutableListOf()) {

}

data class Basket(val values: List<Int>) {
    fun getValueSum(): Int {
        return values.sum();
    }

    fun max(): Int {
        return values.maxOrNull() ?: 0
    }
}

data class Trade(val with: Economist, val weGive: Int, val weGet: Int)

fun solve(input: String): String {

    val lines = input.trimEnd('\n').split(Regex("\r?\n"))
    val numE = lines[0].toInt()
    val economists = lines.subList(1, numE + 1).map {
        it.split(" ").map {
            it.toInt()
        }
    }.map {
        Economist(Basket(it.subList(1, it.size)), it[0])
    }
    val numS = lines[numE + 1].toInt()
    lines.subList(numE + 2, lines.size).forEach {
        val data = it.split(' ')
        for ((index, value) in data.subList(1, data.size).withIndex()) {
            economists[index].preferences.add(value.toInt())
        }
    }

    var output = "";
    for ((i, we) in economists.withIndex()) {
        val weGiveALl = we.basket.values.sortedBy {
            we.preferences[it - 1]
        }!!
        var found = false
        var currentBestTrade: Trade? = null
        var currentBestTradeValue = 0
        for (other in economists) {
            if (we.id == other.id) {
                continue
            }
            val otherAllWeWant = other.basket.values.sortedByDescending {
                we.preferences[it - 1]
            }
            for (weGive in weGiveALl) {
                for (otherGive in otherAllWeWant) {
                    if (other.preferences[weGive - 1] > other.preferences[otherGive - 1]) {
                        val wouldBe = we.preferences[otherGive - 1] - we.preferences[weGive - 1]
                        if (wouldBe > currentBestTradeValue) {
                            currentBestTrade = Trade(other, weGive, otherGive)
                            currentBestTradeValue = wouldBe
                        } else if (wouldBe == currentBestTradeValue && currentBestTrade != null) {
                            if (currentBestTrade.with.id > other.id) {
                                currentBestTrade = Trade(other, weGive, otherGive)
                                currentBestTradeValue = wouldBe
                            } else if (currentBestTrade.with.id == other.id) {
                                if (currentBestTrade.weGive > weGive) {
                                    currentBestTrade = Trade(other, weGive, otherGive)
                                    currentBestTradeValue = wouldBe
                                } else if (currentBestTrade.weGive == weGive) {
                                    if (currentBestTrade.weGet > otherGive) {
                                        currentBestTrade = Trade(other, weGive, otherGive)
                                        currentBestTradeValue = wouldBe
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (currentBestTrade == null) {
            output += "NO TRADE\n"
        } else {
            output += "${we.id} ${currentBestTrade.weGive} ${currentBestTrade.with.id} ${currentBestTrade.weGet}\n"
        }
    }

    return output
}