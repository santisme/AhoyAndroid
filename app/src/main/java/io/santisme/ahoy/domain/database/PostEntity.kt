package io.santisme.ahoy.domain.database

import androidx.room.*

@Entity(tableName = "post_entity")
data class PostEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "avatar_template") val avatarTemplate: String,
    @ColumnInfo(name = "cooked") val cooked: String?,
    @ColumnInfo(name = "blurb") val blurb: String?,
    @ColumnInfo(name = "raw") val raw: String?,
    @ColumnInfo(name = "updated_at") val updatedAt: String?,
    @ColumnInfo(name = "created_at") val createdAt: String?,
    @ColumnInfo(name = "topic_id") val topicId: Int
)

@Dao
interface PostDao {
    @Query(value = "SELECT * FROM post_entity")
    fun getPosts(): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(postList: List<PostEntity>): List<Long>

    @Delete
    fun delete(post: PostEntity)
}

@Database(entities = [PostEntity::class], version = 1)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}