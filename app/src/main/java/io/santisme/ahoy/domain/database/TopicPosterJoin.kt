package io.santisme.ahoy.domain.database

import androidx.room.*

//@Entity(tableName = "topic_poster_join",
//    primaryKeys = ["topicId", "posterId"],
//    foreignKeys = [ForeignKey(entity = TopicEntity::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("topicId"), onDelete = ForeignKey.CASCADE), ForeignKey(entity = PosterEntity::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("posterId"),onDelete = ForeignKey.CASCADE)]
//)
//data class TopicPosterJoinEntity(
//    val topicId: Int,
//    val posterId: Int
//)
//
//@Dao
//interface TopicPosterJoinDao {
//
//    @Insert
//    fun insert(topicPosterJoin: TopicPosterJoinEntity)
//
//    @Query("""
//               SELECT * FROM topic_entity as topic
//               INNER JOIN topic_poster_join as tpj
//               ON topic.id=tpj.topicId
//               WHERE tpj.posterId=:posterId
//               """)
//    fun getTopicsForPoster(posterId: Int): Array<TopicEntity>
//
//    @Query("""
//               SELECT * FROM poster_entity as poster
//               INNER JOIN topic_poster_join as tpj
//               ON poster.id=tpj.posterId
//               WHERE tpj.topicId=:topicId
//               """)
//    fun getPostersForTopic(topicId: Int): Array<PosterEntity>
//
//    @Delete
//    fun delete(topicPosterJoin: TopicPosterJoinEntity)
//}
//
//@Database(entities = [TopicPosterJoinEntity::class], version = 1)
//abstract class TopicPosterJoinDatabase : RoomDatabase() {
//    abstract fun topicPosterJoinDao(): TopicPosterJoinDao
//}