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
    private val _users = MutableStateFlow<NetworkState<List<User>>>(NetworkState.Loading)
    val users: StateFlow<NetworkState<List<User>>> get() = _users
    private var isFetching = false

    fun fetchData() {
        if (isFetching) return

        viewModelScope.launch {
            isFetching = true
            _users.value = NetworkState.Loading
            val result = useCase.invoke()
            _users.value = result.fold(
                onSuccess = { NetworkState.Success(it) },
                onFailure = { NetworkState.Error(it) }
            )
            isFetching = false
        }
    }
}