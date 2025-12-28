package ie.setu.travelnotes.firebase.firestore

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


// Converts Firestore-stored millis to LocalDate
fun PlaceModel.localDate(): LocalDate =
    Instant.ofEpochMilli(dateMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

fun Long.localDate(): LocalDate =
    Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()


// Converts LocalDate to millis for storing in Firestore
fun LocalDate.toMillis(): Long =
    atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

fun String.toMillis(pattern: String = "dd/MM/yyyy"): Long? {
    return try {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val localDate = LocalDate.parse(this, formatter)
        localDate.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    } catch (e: Exception) {
        null
    }
}
