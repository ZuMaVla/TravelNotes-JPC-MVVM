package ie.setu.travelnotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var email: String = "",
    var password: String? = null,
    var uid: String? = null

)