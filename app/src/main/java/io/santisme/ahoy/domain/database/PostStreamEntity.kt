package io.santisme.ahoy.domain.database

import androidx.room.*

//@Entity
//data class PostStreamEntity(
//    @PrimaryKey(autoGenerate = true) val id: Int,
//    @ColumnInfo(name = "posts") val posts: List<PostEntity>
//)
//
//@Dao
//interface PostStreamDao {
//    @Query(value = "SELECT * FROM PostStreamEntity")
//    fun getPostStreams(): List<PostStreamEntity>
//
//    @Query(value = "SELECT * FROM PostStreamEntity WHERE id=:id")
//    fun getPostStreamById(id: Int): List<PostStreamEntity>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(postStreamList: List<PostStreamEntity>): List<Long>
//
//    @Delete
//    fun delete(poster: PostStreamEntity)
//}
//
//@Database(entities = [PostStreamEntity::class], version = 1)
//abstract class PostStreamDatabase : RoomDatabase() {
//    abstract fun postStreamDao(): PostStreamDao
//}

//@Entity(tableName = "post_stream_entity",
//    primaryKeys = ["topicId", "postId"],
//    foreignKeys = [ForeignKey(entity = TopicEntity::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("topicId")), ForeignKey(entity = PostEntity::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("postId"))]
//)
//data class PostStreamEntity(
//    val topicId: Int,
//    val postId: Int
//)
//
//@Dao
//interface PostStreamDao {
//
//    @Insert
//    fun insert(postStream: PostStreamEntity)
//
//    @Query("""
//               SELECT * FROM topic_entity as topic
//               INNER JOIN post_stream_entity as pse
//               ON topic.id=pse.topicId
//               WHERE pse.postId=:postId
//               """)
//    fun getTopicsForPost(postId: Int): Array<TopicEntity>
//
//    @Query("""
//               SELECT * FROM post_entity as post
//               INNER JOIN post_stream_entity as pse
//               ON post.id=pse.postId
//               WHERE pse.topicId=:topicId
//               """)
//    fun getPostsForTopic(topicId: Int): Array<PostEntity>
//
//    @Delete
//    fun delete(postStream: PostStreamEntity)
//}
//
//@Database(entities = [PostStreamEntity::class], version = 1)
//abstract class PostStreamDatabase : RoomDatabase() {
//    abstract fun postStreamDao(): PostStreamDao
//}