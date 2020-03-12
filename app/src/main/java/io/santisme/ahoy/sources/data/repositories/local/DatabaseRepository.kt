package io.santisme.ahoy.sources.data.repositories.local

import android.content.Context
import androidx.room.Room
import io.santisme.ahoy.domain.database.UserDatabase
import io.santisme.ahoy.domain.database.UserEntity
import io.santisme.ahoy.domain.models.UserModel
import kotlin.concurrent.thread

class DatabaseRepository(private val context: Context) : LocalRepositoryProtocol {
    // MARK: - LocalRepositoryProtocol Methods
    // MARK: - Save Methods
    override fun insertAllUsers(userList: List<UserModel>) {
        thread {
            provideUserDatabase().userDao().insertAll(userList = userList.toEntity())
        }
    }

    // MARK: - Delete Methods
    override fun deleteUser(user: UserModel) {
        thread {
            provideUserDatabase().userDao().delete(user = user.toEntity())
        }
    }

    // MARK: - Update Methods
    override fun updateUser(user: UserModel) {
        thread {
            provideUserDatabase().userDao().updateUser(user = user.toEntity())
        }
    }

    // MARK: - Fetch Methods
    override suspend fun fetchLoggedUser(): UserModel? {
        return provideUserDatabase().userDao().fetchLoggedUsers()?.firstOrNull()?.toModel()
    }


    // MARK: - Private Methods
    private fun provideUserDatabase(): UserDatabase =
        Room.databaseBuilder(context, UserDatabase::class.java, "user-database.db").build()

    private fun List<UserEntity>.toModel(): List<UserModel> = map { it.toModel() }

    private fun UserEntity.toModel(): UserModel = UserModel(
        id = id,
        username = username ?: "",
        name = name,
        avatarTemplate = avatarTemplate,
        lastSeenAt = lastSeenAt,
        moderator = moderator,
        logged = logged
    )

    private fun List<UserModel>.toEntity(): List<UserEntity> = map { it.toEntity() }

    private fun UserModel.toEntity(): UserEntity = UserEntity(
        id = id,
        username = username,
        name = name,
        avatarTemplate = avatarTemplate,
        lastSeenAt = lastSeenAt,
        moderator = moderator ?: false,
        logged = logged
    )
}

// MARK: - Protocol DatabaseRepositoryProtocol definition
interface DatabaseRepositoryProtocol {
    fun saveUserList(userList: List<UserModel>)
    fun saveLoggedUser(user: UserModel)
    fun saveUser(user: UserModel)
    fun deleteUser(user: UserModel)
    fun updateUser(user: UserModel)
}