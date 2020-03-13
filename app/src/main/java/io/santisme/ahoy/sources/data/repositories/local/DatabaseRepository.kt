package io.santisme.ahoy.sources.data.repositories.local

import android.content.Context
import androidx.room.Room
import io.santisme.ahoy.domain.database.TopicDatabase
import io.santisme.ahoy.domain.database.TopicEntity
import io.santisme.ahoy.domain.database.UserDatabase
import io.santisme.ahoy.domain.database.UserEntity
import io.santisme.ahoy.domain.models.api.TopicModel
import io.santisme.ahoy.domain.models.api.UserModel
import java.util.*
import kotlin.concurrent.thread

class DatabaseRepository(private val context: Context) : LocalRepositoryProtocol {
    // MARK: - LocalRepositoryProtocol Methods
    // MARK: - Save Methods
    override fun insertAllUsers(userList: List<UserModel>) {
        thread {
            provideUserDatabase().userDao().insertAll(userList = userList.userModelToEntity())
        }
    }

    // MARK: - Delete Methods
    override fun deleteUser(userModel: UserModel) {
        thread {
            provideUserDatabase().userDao().delete(user = userModel.toEntity())
        }
    }

    // MARK: - Update Methods
    override fun updateUser(userModel: UserModel) {
        thread {
            provideUserDatabase().userDao().updateUser(user = userModel.toEntity())
        }
    }

    // MARK: - Fetch Methods
    override suspend fun fetchLoggedUser(): UserModel? {
        return provideUserDatabase().userDao().fetchLoggedUsers()?.firstOrNull()?.toModel()
    }

    override fun insertPrivateMessages(topicList: List<TopicModel>) {
        thread {
            providePrivateMessageDatabase().topicDao().insertTopics(topicList = topicList.topicModelToEntity())
        }
    }

    // MARK: - Private Methods
    private fun provideUserDatabase(): UserDatabase =
        Room.databaseBuilder(context, UserDatabase::class.java, "user-database.db").build()


    private fun providePrivateMessageDatabase(): TopicDatabase = provideTopicDatabase()
    private fun provideTopicDatabase(): TopicDatabase =
        Room.databaseBuilder(context, TopicDatabase::class.java, "topic-database.db").build()

    private fun List<UserEntity>.userEntityToModel(): List<UserModel> = map { it.toModel() }

    private fun UserEntity.toModel(): UserModel =
        UserModel(
            id = id,
            username = username ?: "",
            name = name,
            avatarTemplate = avatarTemplate,
            lastSeenAt = lastSeenAt,
            moderator = moderator,
            logged = logged
        )

    private fun List<UserModel>.userModelToEntity(): List<UserEntity> = map { it.toEntity() }

    private fun UserModel.toEntity(): UserEntity = UserEntity(
        id = id,
        username = username,
        name = name,
        avatarTemplate = avatarTemplate,
        lastSeenAt = lastSeenAt,
        moderator = moderator ?: false,
        logged = logged
    )

    private fun List<TopicEntity>.topicEntityToModel(): List<TopicModel> = map { it.toModel() }

    private fun TopicEntity.toModel(): TopicModel =
        TopicModel(
            id = id,
            views = views,
            title = title ?: "",
            postsCount = postsCount,
            categoryId = categoryId,
            createdAt = createdAt ?: Date().toString()
//            posters = posters.toModel()
        )

    private fun List<TopicModel>.topicModelToEntity(): List<TopicEntity> = map { it.toEntity() }

    private fun TopicModel.toEntity(): TopicEntity = TopicEntity(
        id = id,
        views = views,
        title = title ?: "",
        postsCount = postsCount,
        categoryId = categoryId,
        createdAt = createdAt
//        posters = posters.toEntity()
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