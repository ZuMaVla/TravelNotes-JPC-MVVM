package ie.setu.travelnotes.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UserModel::class,
            parentColumns = ["id"],
            childColumns = ["authorId"],
            onDelete = ForeignKey.CASCADE // If User is deleted, delete their comments too
        ),
        ForeignKey(
            entity = CommentModel::class,
            parentColumns = ["id"],
            childColumns = ["placeId"],
            onDelete = ForeignKey.CASCADE // If Place is deleted, delete its comments too
        )
    ]
)
data class CommentModel(@PrimaryKey(autoGenerate = true)
                        var id: Long = 0L,
                        var placeId: Long = 0L,
                        var authorId: Long = 0L,
                        var date: LocalDateTime = LocalDateTime.now(),
                        var text: String = ""
)