package ie.setu.travelnotes.firebase.auth

sealed class Response<out R> {
    data class Success<out R>(val data: R) : Response<R>()
    data class Error(val exception: Exception) : Response<Nothing>()
    object Loading : Response<Nothing>()
}



