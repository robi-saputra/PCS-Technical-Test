package robi.codingchallenge.networks

import retrofit2.Response
import retrofit2.http.GET
import robi.codingchallenge.networks.data.User

interface ApiService {
    @GET("/getData/test")
    suspend fun fetchData(): Response<List<User>>
}