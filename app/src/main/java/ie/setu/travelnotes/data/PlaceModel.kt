package ie.setu.travelnotes.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class PlaceModel(@PrimaryKey(autoGenerate = true)
                      var id: Long = 0L,
                      var uid: String? = null,
                      var userId: String = "",
                      var name: String = "",
                      var description: String = "",
                      var date: LocalDate = LocalDate.now(),
                      var image: Uri = Uri.EMPTY,
                      var lat: Double = 51.8985,
                      var lng: Double = -8.4756,
                      var rating: Double = 0.0,
                      var public: Boolean = false
)

