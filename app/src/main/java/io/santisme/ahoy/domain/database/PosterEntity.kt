package io.santisme.ahoy.domain.database

import androidx.room.*

//@Entity(tableName = "poster_entity")
//data class PosterEntity(
//    @PrimaryKey(autoGenerate = true) val id: Int,
//    @ColumnInfo(name = "poster_description") val posterDescription: String?,
//    @ColumnInfo(name = "user_id") val userId: Int?
//)
//
//@Dao
//interface PosterDao {
//    @Query(value = "SELECT * FROM poster_entity")
//    fun getPosters(): List<PosterEntity>
//
//    @Query(value = "SELECT * FROM poster_entity WHERE user_id=:userId")
//    fun getPosterByUserId(userId: Int): List<PosterEntity>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(posterList: List<PosterEntity>): List<Long>
//
//    @Delete
//    fun delete(poster: PosterEntity)
//}
//
//@Database(entities = [PosterEntity::class], version = 1)
//abstract class PosterDatabase : RoomDatabase() {
//    abstract fun posterDao(): PosterDao
//}