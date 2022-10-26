package net.shinedev.github.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import net.shinedev.github.entity.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert
    fun insert(person: User?): Long

    @Query("SELECT * FROM User")
    fun findAll(): Cursor?

    @Query("SELECT * FROM User ORDER BY username ASC")
    fun getFavoriteUser(): Flow<List<User>>

    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getById(userId: Long): Flow<User>

    @Query("SELECT * FROM User WHERE userId = :id")
    fun findById(id: Long): Cursor?

    @Query("DELETE FROM User WHERE userId = :id ")
    fun delete(id: Long): Int

    @Update
    fun update(user: User?): Int
}