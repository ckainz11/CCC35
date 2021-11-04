import level3.solve
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.readText


fun main(args: Array<String>) {
    println("...starting")
    val solutions = solveAllInputs();
    saveSolutions(*solutions.toTypedArray())
}

fun solveAllInputs(): List<Solution> {
    var inputs = getAllResourcePaths()
    inputs = inputs.filter { it.fileName.toString().lowercase().endsWith(".txt") }
    return inputs
        .map { Solution(solve(it.readText()), "outputfor_" + it.fileName.toString()) }
}

fun solveInput(i: Number): Solution {
    val input = getAllResourcePaths()
        .find { it.fileName.endsWith(".txt") && (it.fileName.startsWith("input${i}")) } ?: throw FileNotFoundException("Input ${i} was not found")
    return Solution(solve(input.readText()), "solution_" + input.fileName.toString())
}

fun saveSolutions(vararg solutions: Solution) {
    solutions
        .forEach {
            Files.createDirectories(Paths.get("output"))
            File("output/${it.name}.txt").bufferedWriter().use { out ->
                out.write(it.solution)
                println("Saved: ${it.name}")
            }
        }
}