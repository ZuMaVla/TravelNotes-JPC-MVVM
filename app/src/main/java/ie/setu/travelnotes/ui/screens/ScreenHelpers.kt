package ie.setu.travelnotes.ui.screens

import ie.setu.travelnotes.firebase.firestore.Rating

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
