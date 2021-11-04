import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream
import kotlin.io.path.pathString
import kotlin.streams.toList

val anon = object {}

fun getResource(name: String): URL = anon.javaClass.getResource(name)!!

fun getAllResourcePaths(): List<Path> {
    val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
    val resourcesPath = Paths.get(projectDirAbsolutePath, "/src/main/resources")
    return Files.walk(resourcesPath)
        .filter { item -> Files.isRegularFile(item) }
        .toList()
}

fun getResourceAsLines(name: String) = getResourceAsString(name).lines()
fun getResourceAsString(name: String) = getResource(name).readText()


fun getResourceAsInts(name: String) = getResourceAsLines(name).map(String::toInt)

data class Solution(
    val solution: String,
    val name: String,
) {}

class NoSolutionFoundException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}