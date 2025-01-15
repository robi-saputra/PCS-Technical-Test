package pcs.technicaltest.robisaputra.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import robi.codingchallenge.networks.NetworkState
import robi.codingchallenge.networks.data.User
import robi.codingchallenge.networks.usecase.UserUseCase
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val useCase: UserUseCase): ViewModel() {

    private val _users = MutableStateFlow<NetworkState<List<User>>>(NetworkState.Loading())
    val users: StateFlow<NetworkState<List<User>>> get() = _users

    fun fetchData() {
        _users.value = NetworkState.Loading()

        viewModelScope.launch {
            val result = useCase.invoke()
            result.onSuccess { users ->
                _users.value = NetworkState.Success(users)
            }.onFailure { error ->
                _users.value = NetworkState.Error(error)
            }
        }
    }
}