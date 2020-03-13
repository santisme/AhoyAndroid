package io.santisme.ahoy.sources.login

import android.content.Context
import io.santisme.ahoy.domain.models.PasswordRecoveryModel
import io.santisme.ahoy.domain.models.SignInModel
import io.santisme.ahoy.domain.models.SignUpModelWrapper
import io.santisme.ahoy.sources.data.repositories.local.LocalRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginActivityModelView(
    private val view: LoginActivityModelViewProtocol?,
    private val context: Context
) :
    SignInViewControllerDelegate, CoroutineScope {

    private val localRepository = LocalRepository(context = context)

    private val job: CompletableJob = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    companion object {
        enum class SignModelError(error: String) {
            UsernameEmpty("Username is empty"),
            InvalidEmail("Invalid email"),
            PasswordToShort("Password too short, length must be 10 at least characters"),
            NonMatchingPasswords("Passwords don't match"),
            LoginEmpty("Login is empty")
        }
    }

    override fun onViewCreatedWithNoSavedData() {
        val job = async {
            localRepository.getLoggedUser()
        }

        launch(Dispatchers.Main) {
            val response = job.await()

//                view?.enableLoading(false)
            if (response != null && response.logged) {
                view?.launchMainActivity()
            } else {
                view?.navigateToSignIn()
            }
        }

    }

    override fun signInClicked(signInModel: SignInModel) {
        val result = signInModelIsValid(signInModel = signInModel)
        if (result == null) {
//            view?.enableLoading(true)

            val job = async {
                localRepository.signIn(signInModel = signInModel)

            }

            launch(Dispatchers.Main) {
                val response = job.await()

//                view?.enableLoading(false)
                if (response.isSuccessful) {
                    response.body().takeIf { it != null }?.let { signInResponse ->
                        signInResponse.user?.let {
                            localRepository.saveLoggedUser(user = it)
                            view?.launchMainActivity()

                        }

                        signInResponse.errorType?.let {
                            view?.showError(message = it)
                        }
                    }
                        ?: run { view?.showError(message = "Body is null") }
                } else {
                    view?.showError(message = "Username ${signInModel.username} not found")
                }
            }
        } else {
            view?.showError(message = result.toString())
        }
    }

    override fun signInClicked() {
        view?.navigateToSignIn()
    }

    override fun navigateToSignUp() {
        view?.navigateToSignUp()
    }

    override fun signUpClicked(signUpModelWrapper: SignUpModelWrapper) {
        val result = signUpModelIsValid(signUpModelWrapper = signUpModelWrapper)
        if (result == null) {
            val job = async {
                localRepository.signUp(signUpModel = signUpModelWrapper.signUpModel)

            }

            launch(Dispatchers.Main) {
                val response = job.await()

//                view?.enableLoading(false)
                if (response.isSuccessful) {
                    response.body().takeIf { it != null }?.let {
                        view?.navigateToSignIn()
                    }
                        ?: run { view?.showError(message = "Body is null") }
                } else {
                    view?.showError(message = response.message().toString())
                }
            }
        } else {
            view?.showError(message = result.toString())
        }
    }

    override fun buttonPasswordRecovery(passwordRecoveryModel: PasswordRecoveryModel) {
        val result = passwordRecoveryModelIsValid(passwordRecoveryModel = passwordRecoveryModel)
        if (result == null) {
            val job = async {
                localRepository.recoverPassword(passwordRecoveryModel = passwordRecoveryModel)

            }

            launch(Dispatchers.Main) {
                val response = job.await()

//                view?.enableLoading(false)
                if (response.isSuccessful) {
                    response.body().takeIf { it != null }?.let {
                        view?.showSuccess(message = "Email to reset password successfully sent")
                        view?.navigateToSignIn()
                    }
                        ?: run { view?.showError(message = "Body is null") }
                } else {
                    response.errorBody()?.string().takeIf { it != null }?.let {
                        view?.showError(message = it)
                    }
                        ?: run { view?.showError(message = "Unknown Error") }
                }
            }
        } else {
            view?.showError(message = result.toString())
        }
    }

    override fun cancelClicked() {
        view?.navigateToSignIn()
    }

    override fun navigateToPasswordRecovery() {
        view?.navigateToPasswordRecovery()
    }

    // MARK: - Public Methods
    fun signInModelIsValid(signInModel: SignInModel): SignModelError? {
        usernameIsValid(username = signInModel.username)?.let {
            return it
        }

        passwordIsValid(password = signInModel.password)?.let {
            return it
        }

        return null
    }

    fun signUpModelIsValid(signUpModelWrapper: SignUpModelWrapper): SignModelError? {
        usernameIsValid(username = signUpModelWrapper.signUpModel.username)?.let {
            return it
        }

        emailIsValid(email = signUpModelWrapper.signUpModel.email)?.let {
            return it
        }

        passwordIsValid(password = signUpModelWrapper.signUpModel.password)?.let {
            return it
        }

        repeatPasswordIsValid(
            password = signUpModelWrapper.signUpModel.password,
            repeatedPassword = signUpModelWrapper.repeatedPassword
        )?.let {
            return it
        }

        return null
    }

    private fun passwordRecoveryModelIsValid(passwordRecoveryModel: PasswordRecoveryModel): SignModelError? {
        loginIsValid(login = passwordRecoveryModel.login)?.let {
            return it
        }

        return null
    }

    fun usernameIsValid(username: String): SignModelError? {
        if (username.isNullOrEmpty()) {
            return SignModelError.UsernameEmpty
        }
        return null
    }

    fun passwordIsValid(password: String): SignModelError? {
        if (password.count() < 10) {
            return SignModelError.PasswordToShort
        }
        return null
    }

    fun repeatPasswordIsValid(password: String, repeatedPassword: String): SignModelError? {
        if (password != repeatedPassword) {
            return SignModelError.NonMatchingPasswords
        }
        return null
    }

    fun emailIsValid(email: String): SignModelError? {
        if (!"""^\w+\@\w+\.{1}\w{2,3}$""".toRegex().matches(email)) {
            return SignModelError.InvalidEmail
        }
        return null
    }

    fun loginIsValid(login: String): SignModelError? {
        if (login.isNullOrEmpty()) {
            return SignModelError.LoginEmpty
        }
        return null
    }
}

interface LoginActivityModelViewProtocol {
    fun navigateToSignIn()
    fun launchMainActivity()
    fun navigateToSignUp()
    fun showError(message: String)
    fun showSuccess(message: String)

    //    fun navigateBack()
    fun navigateToPasswordRecovery()
}