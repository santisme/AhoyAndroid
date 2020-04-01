package io.santisme.ahoy.domain.database

import androidx.room.*

@Entity(tableName = "topic_entity")
data class TopicEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "category_id") val categoryId: Int,
    @ColumnInfo(name = "created_at") val createdAt: String?,
    @ColumnInfo(name = "posts_count") val postsCount: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "views") val views: Int,
    @ColumnInfo(name = "bumped") val bumped: Boolean?,
    @ColumnInfo(name = "archetype") val archetype: String?
//    @TypeConverters(ListConverter::class)
//    @Embedded val posters: List<PosterEntity>?
//    @Embedded val postStream: PostStreamEntity?,
//    @Embedded val topicDetails: TopicDetailsEntity?
)

data class PosterEntity(val userId: Int?, val posterDescription: String?)
//data class PostStreamEntity(val posts: List<PostEntity>)
//data class TopicDetailsEntity(val createdBy: CreatedByEntity)
//data class CreatedByEntity(val user: UserEntity)

@Dao
interface TopicDao {
    @Query(value = "SELECT * FROM topic_entity")
    fun getTopics(): List<TopicEntity>

    @Query(value = "SELECT * FROM topic_entity WHERE id=:id")
    fun getTopicById(id: Int): List<TopicEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopics(topicList: List<TopicEntity>): List<Long>

    @Delete
    fun delete(topic: TopicEntity)
}

@Database(entities = [TopicEntity::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class TopicDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
}