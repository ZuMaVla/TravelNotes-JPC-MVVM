package ie.setu.travelnotes.ui.screens

import ie.setu.travelnotes.firebase.firestore.Rating

const val IMAGE_PLACEHOLDER_URI = "https://firebasestorage.googleapis.com/v0/b/setu-zumavla.firebasestorage.app/o/images%2Fdefault.png?alt=media&token=475946d5-4a64-4152-9ac4-9d6e315a7d31"

fun getAvgRating(ratings: List<Rating>): Double {
   if (ratings.isEmpty()) {
       return 0.0
   } else {
       var sum = 0.0
       for (rating in ratings) {
           sum += rating.value
       }
       return sum / ratings.size
   }
}

fun getIndividualRating(ratings: List<Rating>, userId: String): Int {
    var individualRating = 0
    for (rating in ratings) {
        if (rating.userId == userId) {
            individualRating = rating.value
        }
    }
    return individualRating
}
