package net.shinedev.core.data.source.local


import kotlinx.coroutines.flow.Flow
import net.shinedev.core.data.source.local.entity.UserEntity
import net.shinedev.core.data.source.local.room.UserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val userDao: UserDao) {
    fun getListUser(queryParams: String): Flow<List<UserEntity>?> = userDao.getListUser(queryParams)

    fun getDetailUser(username: String): Flow<UserEntity?> = userDao.getDetailUser(username)

    fun inserts(users: List<UserEntity>?) = userDao.inserts(users)

    fun getFavoriteUser(queryParams: String): Flow<List<UserEntity>?> =
        userDao.getFavoriteUser(queryParams)

    fun setFavoriteUser(user: UserEntity, isFavorite: Boolean) =
        userDao.setAsFavorite(user.id.toLong(), isFavorite)
}