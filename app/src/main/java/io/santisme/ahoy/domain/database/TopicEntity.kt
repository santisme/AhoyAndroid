package io.santisme.ahoy.domain.database

import androidx.room.*

//@Entity
//data class TopicEntity(
//    @PrimaryKey(autoGenerate = false) val id: Int,
//    @ColumnInfo(name = "category_id") val categoryId: String,
//    @ColumnInfo(name = "created_at") val createdAt: String?,
//    @ColumnInfo(name = "post_count") val postCount: Int,
//    @ColumnInfo(name = "title") val title: String?,
//    @ColumnInfo(name = "views") val views: Int,
//    @ColumnInfo(name = "posters") val posters: List<PosterEntity>?
////    @ColumnInfo(name = "post_stream") val postStream: PostStreamEntiry?
//)
//
//@Dao
//interface TopicDao {
//    @Query(value = "SELECT * FROM TopicEntity")
//    fun getPosters(): List<PosterEntity>
//
//    @Query(value = "SELECT * FROM TopicEntity WHERE id=:id")
//    fun getTopicById(id: Int): List<TopicEntity>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(topicList: List<TopicEntity>): List<Long>
//
//    @Delete
//    fun delete(topic: TopicEntity)
//}
//
//@Database(entities = [UserEntity::class], version = 1)
//abstract class TopicDabase : RoomDatabase() {
//    abstract fun topicDao(): TopicDao
//}