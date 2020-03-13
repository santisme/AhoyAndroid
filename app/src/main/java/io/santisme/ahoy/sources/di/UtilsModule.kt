package io.santisme.ahoy.sources.di

//import android.content.Context
//import androidx.room.Room
//import dagger.Module
//import dagger.Provides
//import io.santisme.ahoy.domain.database.UserDatabase
//import javax.inject.Singleton
//
//@Module
//class UtilsModule(private val context: Context) {
//
//    @Singleton
//    @Provides
//    fun provideApplicationContext(): Context = context
//
//    @Singleton
//    @Provides
//    fun provideUSerDatabase(): UserDatabase = Room.databaseBuilder(
//        context, UserDatabase::class.java, "user-database.db"
//    ).build()
//}