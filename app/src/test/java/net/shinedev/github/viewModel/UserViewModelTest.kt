package net.shinedev.github.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import net.shinedev.core.data.Resource
import net.shinedev.core.data.model.UserImpl
import net.shinedev.core.domain.model.User
import net.shinedev.core.domain.usecase.UserUseCase
import net.shinedev.github.viewModel.utils.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var userViewModel: UserViewModel

    @Mock
    private lateinit var useCase: UserUseCase

    @Before
    fun setUp() {
        userViewModel = UserViewModel(useCase)
    }

    @Test
    fun `when getListUser then return failed`() = runTest {
        // GIVEN
        val queryParams = ""
        val expectedResponse: Flow<Resource<List<User>>> =
            flow { emit(Resource.Error("")) }

        `when`(useCase.getListUser(queryParams)).thenReturn(expectedResponse)

        val actualResponse = userViewModel.getListUser(queryParams)

        assertEquals(expectedResponse.asLiveData().value?.message, actualResponse.value?.message)

        verify(useCase).getListUser(queryParams)
    }

    @Test
    fun `when getListUser then return success`() = runTest {
        // GIVEN
        val queryParams = ""
        val expectedResponse: Flow<Resource<List<User>>> =
            flow { emit(Resource.Success(listOf(UserImpl()))) }

        `when`(useCase.getListUser(queryParams)).thenReturn(expectedResponse)

        val actualResponse = userViewModel.getListUser(queryParams)

        assertEquals(expectedResponse.asLiveData().value?.data, actualResponse.value?.data)

        verify(useCase).getListUser(queryParams)
    }

    @Test
    fun `when getDetailUser then return failed`() = runTest {
        // GIVEN
        val queryParams = ""
        val expectedResponse: Flow<Resource<User>> =
            flow { emit(Resource.Error("")) }

        `when`(useCase.getDetailUser(queryParams)).thenReturn(expectedResponse)

        val actualResponse = userViewModel.getDetailUser(queryParams)

        assertEquals(expectedResponse.asLiveData().value?.message, actualResponse.value?.message)

        verify(useCase).getDetailUser(queryParams)
    }

    @Test
    fun `when getDetailUser then return success`() = runTest {
        // GIVEN
        val queryParams = ""
        val expectedResponse: Flow<Resource<User>> =
            flow { emit(Resource.Success(UserImpl())) }

        `when`(useCase.getDetailUser(queryParams)).thenReturn(expectedResponse)

        val actualResponse = userViewModel.getDetailUser(queryParams)

        assertEquals(expectedResponse.asLiveData().value?.data, actualResponse.value?.data)

        verify(useCase).getDetailUser(queryParams)
    }

    @Test
    fun `when getFollower then return failed`() = runTest {
        // GIVEN
        val queryParams = ""
        val expectedResponse: Flow<Resource<List<User>>> =
            flow { emit(Resource.Error("")) }

        `when`(useCase.getFollower(queryParams)).thenReturn(expectedResponse)

        val actualResponse = userViewModel.getFollower(queryParams)

        assertEquals(expectedResponse.asLiveData().value?.message, actualResponse.value?.message)

        verify(useCase).getFollower(queryParams)
    }

    @Test
    fun `when getFollower then return success`() = runTest {
        // GIVEN
        val queryParams = ""
        val expectedResponse: Flow<Resource<List<User>>> =
            flow { emit(Resource.Success(listOf(UserImpl()))) }

        `when`(useCase.getFollower(queryParams)).thenReturn(expectedResponse)

        val actualResponse = userViewModel.getFollower(queryParams)

        assertEquals(expectedResponse.asLiveData().value?.data, actualResponse.value?.data)

        verify(useCase).getFollower(queryParams)
    }

    @Test
    fun `when getFollowing then return failed`() = runTest {
        // GIVEN
        val queryParams = ""
        val expectedResponse: Flow<Resource<List<User>>> =
            flow { emit(Resource.Error("")) }

        `when`(useCase.getFollowing(queryParams)).thenReturn(expectedResponse)

        val actualResponse = userViewModel.getFollowing(queryParams)

        assertEquals(expectedResponse.asLiveData().value?.message, actualResponse.value?.message)

        verify(useCase).getFollowing(queryParams)
    }

    @Test
    fun `when getFollowing then return success`() = runTest {
        // GIVEN
        val queryParams = ""
        val expectedResponse: Flow<Resource<List<User>>> =
            flow { emit(Resource.Success(listOf(UserImpl()))) }

        `when`(useCase.getFollowing(queryParams)).thenReturn(expectedResponse)

        val actualResponse = userViewModel.getFollowing(queryParams)

        assertEquals(expectedResponse.asLiveData().value?.data, actualResponse.value?.data)

        verify(useCase).getFollowing(queryParams)
    }

    @Test
    fun `when setFavorite then return success`() = runTest {
        // GIVEN
        val user = UserImpl()
        val isFavorite = Random.nextBoolean()
        val expectedResponse: Flow<Int> =
            flow { emit(1) }

        `when`(useCase.setFavoriteUser(user,isFavorite)).thenReturn(expectedResponse)

        val actualResponse = userViewModel.setFavorite(user, isFavorite)

        assertEquals(expectedResponse, actualResponse)

        verify(useCase).setFavoriteUser(user, isFavorite)
    }
}