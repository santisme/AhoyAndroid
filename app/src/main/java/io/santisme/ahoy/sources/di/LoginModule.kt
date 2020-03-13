package io.santisme.ahoy.sources.di

//import android.content.Context
//import dagger.Module
//import dagger.Provides
//import io.santisme.ahoy.domain.database.UserDatabase
//import io.santisme.ahoy.sources.data.repositories.local.DatabaseRepository
//import io.santisme.ahoy.sources.data.repositories.local.LocalRepository
//import io.santisme.ahoy.sources.login.LoginActivityModelView
//import io.santisme.ahoy.sources.login.LoginActivityModelViewProtocol
//import io.santisme.ahoy.sources.login.SignInViewControllerDelegate
//import javax.inject.Singleton
//
//@Module
//class LoginModule(private val view: LoginActivityModelViewProtocol) {
//
//    @Provides
//    fun provideLoginActivityView(): LoginActivityModelViewProtocol = view
//
//    @Singleton
//    @Provides
//    fun provideLocalRepository(databaseRepository: DatabaseRepository): LocalRepository {
//        LocalRepository.database = databaseRepository
//        return LocalRepository
//    }
//
//    @Singleton
//    @Provides
//    fun provideDatabaseRepository(userDatabase: UserDatabase): DatabaseRepository =
//        DatabaseRepository.apply { userDB = userDatabase }
//
//
////    @Singleton
////    @Provides
////    fun provideLoginActivityModelView(injectedLocalRepository: LocalRepository): SignInViewControllerDelegate =
////        LoginActivityModelView(view = view, localRepository = injectedLocalRepository)
//
//
//}
