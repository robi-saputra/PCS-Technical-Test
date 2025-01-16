package robi.codingchallenge.networks

sealed class NetworkState<out T>(
    /*val data: T? = null,
    val message: Throwable? = null*/
) {
    //class Success<T>(data: T) : NetworkState<T>(data)
    class Success<out T>(val data: T) : NetworkState<T>() {
        override fun equals(other: Any?): Boolean {
            return other is Success<*> && other.data == data
        }

        override fun hashCode(): Int {
            return data?.hashCode() ?: 0
        }
    }

    //class Error<T>(message: Throwable, data: T? = null) : NetworkState<T>(data, message)
    class Error(val exception: Throwable) : NetworkState<Nothing>() {
        override fun equals(other: Any?): Boolean {
            return other is Error && other.exception.message == exception.message
        }

        override fun hashCode(): Int {
            return exception.message?.hashCode() ?: 0
        }
    }

    //class Loading<T> : NetworkState<T>()
    data object Loading : NetworkState<Nothing>()
}