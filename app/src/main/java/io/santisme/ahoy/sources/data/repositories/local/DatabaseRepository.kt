package io.santisme.ahoy.sources.data.repositories.local

import android.content.Context
import androidx.room.Room
import io.santisme.ahoy.domain.database.*
import io.santisme.ahoy.domain.models.api.*
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
            providePrivateMessageDatabase().topicDao()
                .insertTopics(topicList = topicList.topicModelToEntity())
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
            createdAt = createdAt ?: Date().toString(),
            posters = null,
//            posters = posters?.posterEntityToModel(),
            archetype = archetype,
            bumped = bumped
//            postStream = postStream?.toModel(),
//            topicDetails = topicDetails?.toModel()
        )

    private fun List<TopicModel>.topicModelToEntity(): List<TopicEntity> = map { it.toEntity() }

    private fun TopicModel.toEntity(): TopicEntity = TopicEntity(
        id = id,
        views = views ?: 0,
        title = title,
        postsCount = postsCount,
        categoryId = categoryId ?: 0,
        createdAt = createdAt,
//        posters = posters?.posterModelToEntity(),
//        postStream = postStream?.toEntity(),
        bumped = bumped,
        archetype = archetype
//        topicDetails = topicDetails?.toEntity()
    )

    private fun List<PosterEntity>.posterEntityToModel(): List<PosterModel> = map { it.toModel() }

    private fun PosterEntity.toModel(): PosterModel =
        PosterModel(
            description = posterDescription ?: "",
            userId = userId
        )

    private fun List<PosterModel>.posterModelToEntity(): List<PosterEntity> = map { it.toEntity() }

    private fun PosterModel.toEntity(): PosterEntity =
        PosterEntity(
//            id = 0,
            userId = userId,
            posterDescription = description
        )

//    private fun List<PostStreamEntity>.postStreamEntityToModel(): List<PostStreamModel> =
//        map { it.toModel() }
//
//    private fun PostStreamEntity.toModel(): PostStreamModel =
//        PostStreamModel(
//            posts = posts?.postEntityToModel() ?: listOf()
//        )
//
//    private fun List<PostStreamModel>.postStreamModelToEntity(): List<PostStreamEntity> =
//        map { it.toEntity() }
//
//    private fun PostStreamModel.toEntity(): PostStreamEntity =
//        PostStreamEntity(
////            id = 0,
//            posts = posts.postModelToEntity()
//        )

    private fun List<PostEntity>.postEntityToModel(): List<PostModel> = map { it.toModel() }

    private fun PostEntity.toModel(): PostModel =
        PostModel(
            id = id,
            name = name,
            username = username,
            avatarTemplate = avatarTemplate,
            cooked = cooked,
            blurb = blurb,
            raw = raw,
            updatedAt = updatedAt,
            createdAt = createdAt,
            topicId = topicId
        )

    private fun List<PostModel>.postModelToEntity(): List<PostEntity> = map { it.toEntity() }

    private fun PostModel.toEntity(): PostEntity = PostEntity(
        id = id,
        name = name,
        username = username,
        avatarTemplate = avatarTemplate,
        cooked = cooked,
        blurb = blurb,
        raw = raw,
        updatedAt = updatedAt,
        createdAt = createdAt,
        topicId = topicId
    )

//    private fun List<TopicDetailsEntity>.topicDetailsEntityToModel(): List<TopicDetailsModel> =
//        map { it.toModel() }
//
//    private fun TopicDetailsEntity.toModel(): TopicDetailsModel =
//        TopicDetailsModel(
//            topicCreatedBy = createdBy.user.toModel()
//        )
//
//    private fun List<TopicDetailsModel>.topicDetailsModelToEntity(): List<TopicDetailsEntity> =
//        map { it.toEntity() }
//
//    private fun TopicDetailsModel.toEntity(): TopicDetailsEntity =
//        TopicDetailsEntity(
//            createdBy = CreatedByEntity(user = topicCreatedBy.toEntity())
//        )
}

// MARK: - Protocol DatabaseRepositoryProtocol definition
interface DatabaseRepositoryProtocol {
    fun saveUserList(userList: List<UserModel>)
    fun saveLoggedUser(user: UserModel)
    fun saveUser(user: UserModel)
    fun deleteUser(user: UserModel)
    fun updateUser(user: UserModel)
}