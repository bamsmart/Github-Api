package net.shinedev.core.data.source.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import net.shinedev.core.data.source.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun inserts(user: List<UserEntity>?)

    @Update
    fun update(user: UserEntity?)

    @Query("DELETE FROM UserEntity WHERE user_id = :id ")
    fun delete(id: Long): Int

    @Query("SELECT * FROM UserEntity WHERE user_name LIKE '%'|| :queryParams ||'%' ORDER BY user_name ASC")
    fun getListUser(queryParams: String): Flow<List<UserEntity>?>

    @Query("SELECT * FROM UserEntity WHERE user_name = :username")
    fun getDetailUser(username: String): Flow<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE user_name LIKE '%'|| :queryParams ||'%' AND is_favorite = 1 ORDER BY user_name ASC")
    fun getFavoriteUser(queryParams: String): Flow<List<UserEntity>?>

    @Query("SELECT * FROM UserEntity WHERE user_id = :userId")
    fun getUserById(userId: Long): Flow<UserEntity>

    @Query("UPDATE UserEntity SET is_favorite = :isFavorite WHERE user_id = :id ")
    fun setAsFavorite(id: Long, isFavorite : Boolean): Int
}