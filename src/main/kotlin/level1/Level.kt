package level1

fun solve(input: String): String {
    val lines = input.split(Regex("\r?\n"))
    val data = lines.map { it.toInt() }
    return data.sum().toString()
}