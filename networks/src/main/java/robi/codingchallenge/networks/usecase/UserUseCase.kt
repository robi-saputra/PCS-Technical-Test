package robi.codingchallenge.networks.usecase

import robi.codingchallenge.networks.data.User
import robi.codingchallenge.networks.repository.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(): Result<List<User>> {
        return try {
            val response = repository.fetchData()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("No data"))
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}