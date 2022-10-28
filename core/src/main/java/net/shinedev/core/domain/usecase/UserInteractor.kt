package net.shinedev.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import net.shinedev.core.data.Resource
import net.shinedev.core.domain.model.User
import net.shinedev.core.domain.repository.UserRepository
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: UserRepository):
    UserUseCase {
    override fun getListUser(queryParams: String): Flow<Resource<List<User>>>  = userRepository.getListUser(queryParams)

    override fun getDetailUser(username: String): Flow<Resource<User>>  = userRepository.getDetailUser(username)

    override fun getFollower(username: String): Flow<Resource<List<User>>> = userRepository.getFollowers(username)

    override fun getFollowing(username: String): Flow<Resource<List<User>>> = userRepository.getFollowings(username)

    override fun setFavoriteUser(user: User, isFavorite: Boolean) = userRepository.setFavoriteUser(user,isFavorite)

    override fun getFavoriteUser(queryParams: String): Flow<Resource<List<User>>>  = userRepository.getFavoriteUser(queryParams)
}