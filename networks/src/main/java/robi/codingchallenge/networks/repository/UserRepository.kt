package robi.codingchallenge.networks.repository

import retrofit2.Response
import robi.codingchallenge.networks.ApiService
import robi.codingchallenge.networks.data.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchData(): Response<List<User>> {
        val response = apiService.fetchData()
        if (response.isSuccessful) {
            return response
        } else {
            throw Exception("Network request failed")
        }
    }
}