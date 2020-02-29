package io.santisme.ahoy.sources.login

class LoginActivityModelView(private val view: LoginActivityModelViewProtocol) :
    SignInViewControllerDelegate {

    override fun onViewCreatedWithNoSavedData() {
//        if (userRepo.isLogged()) {
//        view?.launchTopicsActivity()
//        } else {
        view.navigateToSignIn()
//        }
    }

}

interface LoginActivityModelViewProtocol {
    fun navigateToSignIn()
    fun launchTopicsActivity()
}