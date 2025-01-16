import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import pcs.technicaltest.robisaputra.ui.user.UserViewModel
import robi.codingchallenge.networks.NetworkState
import robi.codingchallenge.networks.data.User
import robi.codingchallenge.networks.usecase.UserUseCase

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {

    @Mock
    private lateinit var userUseCase: UserUseCase

    private lateinit var userViewModel: UserViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        userViewModel = UserViewModel(userUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should handle network errors gracefully`() = runTest {
        val mockError = IOException("Network Error")
        `when`(userUseCase.invoke()).thenReturn(Result.failure(mockError))

        userViewModel.fetchData()
        testDispatcher.scheduler.advanceUntilIdle()

        val actualState = userViewModel.users.first()
        assertEquals(NetworkState.Error(mockError), actualState)
    }

    @Test
    fun `should fetch data when fetchData function is called`() = runTest {
        val mockUsers = listOf(
            User(
                "2024-08-25T21:17:00.287Z",
                "Emmett Dietrich",
                "https://loremflickr.com/640/480/people",
                "Palmdale",
                "Tonga",
                "Bedfordshire",
                "2732",
                "Bednar Crossroad",
                "38983",
                "1"
            )
        )
        `when`(userUseCase.invoke()).thenReturn(Result.success(mockUsers))

        userViewModel.fetchData()
        testDispatcher.scheduler.advanceUntilIdle()

        val actualState = userViewModel.users.first()
        assertEquals(NetworkState.Success(mockUsers), actualState)
    }
    
    @Test
    fun `should return empty list when no users are available`() = runTest {
        val emptyUserList = emptyList<User>()
        `when`(userUseCase.invoke()).thenReturn(Result.success(emptyUserList))

        userViewModel.fetchData()
        testDispatcher.scheduler.advanceUntilIdle()

        val actualState = userViewModel.users.first()
        assertEquals(NetworkState.Success(emptyUserList), actualState)
    }
    
    @Test
    fun `should not fetch data when already fetching data`() = runTest {
        val mockUsers = listOf(
            User(
                "2024-08-25T21:17:00.287Z",
                "Emmett Dietrich",
                "https://loremflickr.com/640/480/people",
                "Palmdale",
                "Tonga",
                "Bedfordshire",
                "2732",
                "Bednar Crossroad",
                "38983",
                "1"
            )
        )
        `when`(userUseCase.invoke()).thenReturn(Result.success(mockUsers))

        userViewModel.fetchData()
        testDispatcher.scheduler.advanceUntilIdle()

        // Simulate another fetchData call while the first one is still running
        userViewModel.fetchData()

        // Verify that the second fetchData call does not change the state
        val actualState = userViewModel.users.first()
        assertEquals(NetworkState.Success(mockUsers), actualState)
    }


}