package io.santisme.ahoy.domain.database

import androidx.room.*

@Entity
data class TopicEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "category_id") val categoryId: Int,
    @ColumnInfo(name = "created_at") val createdAt: String?,
    @ColumnInfo(name = "posts_count") val postsCount: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "views") val views: Int
//    @ColumnInfo(name = "posters") val posters: List<PosterEntity>?
//    @ColumnInfo(name = "post_stream") val postStream: PostStreamEntity?
)

@Dao
interface TopicDao {
    @Query(value = "SELECT * FROM TopicEntity")
    fun getTopics(): List<TopicEntity>

    @Query(value = "SELECT * FROM TopicEntity WHERE id=:id")
    fun getTopicById(id: Int): List<TopicEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopics(topicList: List<TopicEntity>): List<Long>

    @Delete
    fun delete(topic: TopicEntity)
}

@Database(entities = [TopicEntity::class], version = 1)
abstract class TopicDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
}