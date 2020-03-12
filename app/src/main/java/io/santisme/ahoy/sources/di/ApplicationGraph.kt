package io.santisme.ahoy.sources.di

//import dagger.Component
//import io.santisme.ahoy.sources.login.LoginActivity
//import io.santisme.ahoy.sources.login.LoginActivityModelView
//import io.santisme.ahoy.sources.login.SignInViewControllerDelegate
//import javax.inject.Singleton
//
//@Singleton
//// @Component makes Dagger to create a graph of dependencies
//@Component(
//    modules = [UtilsModule::class, LoginModule::class, LoginAbstractModule::class]
//)
//interface ApplicationGraph {
//    // Add here as well functions whose input argument is the entity in which Dagger
//    // can add any dependency you want
//    fun inject(loginActivity: LoginActivity)
//    fun inject(loginActivityModelView: LoginActivityModelView)
//    fun inject(LoginActivityModelView: SignInViewControllerDelegate)
//
//}