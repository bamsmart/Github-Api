package net.shinedev.github.repository

import net.shinedev.github.dao.UserDao
import net.shinedev.github.entity.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val allFavoriteUser: Flow<List<User>> = userDao.getFavoriteUser()

    fun getFavoriteUser(userId: Long): Flow<User> {
        return userDao.getById(userId)
    }

}