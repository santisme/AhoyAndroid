package io.santisme.ahoy.domain.database

import androidx.room.*

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "avatar_template") val avatarTemplate: String?,
    @ColumnInfo(name = "last_seen_at") val lastSeenAt: String?,
    @ColumnInfo(name = "logged") val logged: Boolean,
    @ColumnInfo(name = "moderator") val moderator: Boolean,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "username") val username: String?
//    @ColumnInfo(name = "poster") val poster: List<PosterEntity>?

)

@Dao
interface UserDao {
    @Query(value = "SELECT * FROM UserEntity WHERE logged")
    fun fetchLoggedUsers(): List<UserEntity>?

    @Query(value = "SELECT * FROM UserEntity")
    fun fetchUsers(): List<UserEntity>

    @Query(value = "SELECT * FROM UserEntity WHERE id=:id")
    fun fetchUserById(id: Int): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(userList: List<UserEntity>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

}

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}