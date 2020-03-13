package io.santisme.ahoy.domain.database

import androidx.room.*

//@Entity
//data class PosterEntity(
//    @PrimaryKey(autoGenerate = false) val userId: Int?,
//    @ColumnInfo(name = "topic") val topic: TopicEntity?,
//    @ColumnInfo(name = "poster_description") val posterDescription: String?
//)
//
//@Dao
//interface PosterDao {
//    @Query(value = "SELECT * FROM PosterEntity")
//    fun getPosters(): List<PosterEntity>
//
//    @Query(value = "SELECT * FROM PosterEntity WHERE userId=:userId")
//    fun getPosterByUserId(userId: Int): List<UserEntity>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(posterList: List<PosterEntity>): List<Long>
//
//    @Delete
//    fun delete(poster: PosterEntity)
//}
//
//@Database(entities = [UserEntity::class], version = 1)
//abstract class PosterDatabase : RoomDatabase() {
//    abstract fun posterDao(): PosterDao
//}