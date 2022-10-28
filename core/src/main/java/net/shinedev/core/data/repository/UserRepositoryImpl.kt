package net.shinedev.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import net.shinedev.core.data.NetworkBoundResource
import net.shinedev.core.data.Resource
import net.shinedev.core.data.model.UserImpl
import net.shinedev.core.data.source.local.LocalDataSource
import net.shinedev.core.data.source.remote.RemoteDataSource
import net.shinedev.core.data.source.remote.network.ApiResponse
import net.shinedev.core.data.source.remote.response.UserResponse
import net.shinedev.core.domain.model.User
import net.shinedev.core.domain.repository.UserRepository
import net.shinedev.core.utils.AppExecutors
import net.shinedev.core.utils.DataMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : UserRepository {

    override fun getListUser(queryParams: String): Flow<Resource<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserResponse>>() {
            override fun loadFromDB(): Flow<List<User>> {
                return localDataSource.getListUser(queryParams).map {
                    DataMapper.mapEntitiesToDomain(it) ?: listOf()
                }
            }
            override fun shouldFetch(data: List<User>?): Boolean = data != null

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getListUser(queryParams)

            override suspend fun saveCallResult(data: List<UserResponse>) {
                val userList = DataMapper.mapResponsesToEntities(data)
                appExecutors.diskIO().execute {
                    localDataSource.inserts(userList)
                }
            }
        }.asFlow()

    override fun getDetailUser(username: String): Flow<Resource<User>> = flow {
        val result = remoteDataSource.getDetailUser(username)
        when (result.first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success((result.first() as ApiResponse.Success<UserResponse>).data))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success(UserImpl()))
            }
            else -> {
                emit(Resource.Error(""))
            }
        }
    }

    override fun getFollowers(username: String): Flow<Resource<List<User>>> = flow {
        val result = remoteDataSource.getFollower(username)
        emit(Resource.Loading())
        when (result.first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success((result.first() as ApiResponse.Success<List<UserResponse>>).data))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success(listOf()))
            }
            else -> {
                emit(Resource.Error(""))
            }
        }
    }

    override fun getFollowings(username: String): Flow<Resource<List<User>>> = flow {
        val result = remoteDataSource.getFollowing(username)
        emit(Resource.Loading())
        when (result.first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success((result.first() as ApiResponse.Success<List<UserResponse>>).data))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success(listOf()))
            }
            else -> {
                emit(Resource.Error(""))
            }
        }
    }

    override suspend fun getProfile(username: String): Flow<ApiResponse<User>> =
        remoteDataSource.getDetailUser(username)

    override fun setFavoriteUser(user: User, isFavorite: Boolean): Flow<Int> = flow {
        val userEntity = DataMapper.mapDomainToEntity(user)
        val rowEffected = localDataSource.setFavoriteUser(userEntity, isFavorite)
        emit(rowEffected)
    }

    override fun getFavoriteUser(queryParams: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        try {
            val result = localDataSource.getFavoriteUser(queryParams)
            if (result.first().isNullOrEmpty()) {
                emit(Resource.Error(""))
            } else {
                emit(Resource.Success(result.first() as List<User>))
            }
        } catch (e: Exception) {
            emit(Resource.Error(""))
        }
    }
}