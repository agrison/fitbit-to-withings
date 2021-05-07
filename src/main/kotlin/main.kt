import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern
import java.util.zip.ZipFile

fun main(args: Array<String>) {
    chunkedEntries(args[0], ".*weight.*.json$") { weightEntry(it) }
        .forEachIndexed { n, list -> writeCsv(n, "Date,Weight (Kg),Fat (Kg)", list) }
}

fun writeCsv(n: Int, header: String, lines: List<String>) {
    File("weight_${n + 1}.csv").also {
        it.writeText(lines.csv(header))
        println("Created ${it.absolutePath}")
    }
}

fun <T> chunkedEntries(path: String, regex: String, func: (j: String) -> List<T>): List<List<String>> {
    return ZipFile(path).use { zip ->
        zip.entries().toList()
            .filter { x -> x.name.matches(Regex(regex)) }
            .map { entry ->
                zip.getInputStream(entry).use { input ->
                    input.bufferedReader().use { it.readText() }.let {
                        func(it).map { x -> x.toString() }
                    }
                }
            }
    }.flatten().let {
        println("Loaded ${it.size} entries.")
        it.chunked(299)
    }
}

// region Model
data class WeightEntry(val weight: Double, val fat: Double, val date: String, val time: String) {
    override fun toString() = "${moment(date, time)},${weight.toKgs()},${(fat / 100 * weight).toKgs()}"
}

val gson: Gson = GsonBuilder().create()
val weightEntryType: Type = object : TypeToken<List<WeightEntry>>() {}.type

fun weightEntry(json: String): List<WeightEntry> = gson.fromJson(json, weightEntryType)
// endregion

// region Extensions
fun moment(date: String, time: String) =
    LocalDate.parse(date, ofPattern("MM/dd/yy")).toString() + " " + time

fun Double.toKgs() = if (this == 0.0) "" else (this * 0.453592).toString()
fun List<String>.csv(header: String) = header + "\n" + this.joinToString("\n")
// endregion